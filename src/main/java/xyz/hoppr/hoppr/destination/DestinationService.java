package xyz.hoppr.hoppr.destination;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import xyz.hoppr.hoppr.exception.NotFoundException;
import xyz.hoppr.hoppr.exception.UnauthorizedException;
import xyz.hoppr.hoppr.exception.UnprocessableEntityException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DestinationService {

    @Autowired
    private DestinationMapper destinationMapper;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private RecentUserDestinationRepository recentUserDestinationRepository;

    @Autowired
    private FavoriteDestinationRepository favoriteDestinationRepository;

    @Value("${sabre.accesstoken}")
    private String sabreAccessToken;

    @Value("${yelp.apikey}")
    private String yelpApiKey;

    public DestinationEntity getDestinationEntity(String slug) {
        return destinationRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException(String.format("Destination %s not found", slug)));
    }

    public List<Destination> getDestinations() {
        return destinationRepository.findAll().stream()
                .map(destinationEntity -> destinationMapper.map(destinationEntity))
                .collect(Collectors.toList());
    }

    public Destination getDestination(String slug, String userId) {
        DestinationEntity destinationEntity = getDestinationEntity(slug);

        if (userId != null) {
            RecentUserDestinationEntity recentUserDestinationEntity = recentUserDestinationRepository.findByUserIdAndDestinationId(userId, destinationEntity.getDestinationId());
            if (recentUserDestinationEntity != null) {
                recentUserDestinationEntity.setLastVisitedDate(OffsetDateTime.now());
            } else {
                recentUserDestinationEntity = RecentUserDestinationEntity.builder()
                        .userId(userId)
                        .destinationId(destinationEntity.getDestinationId())
                        .lastVisitedDate(OffsetDateTime.now())
                        .build();
            }
            recentUserDestinationRepository.save(recentUserDestinationEntity);
        }

        return destinationMapper.map(destinationEntity);
    }

    public List<SabreDestinations> getTopSabreDestinations(String theme) {
        final String uri = "https://api-crt.cert.havail.sabre.com/v1/lists/top/destinations?destinationtype=domestic&lookbackweeks=12&topdestinations=50&origincountry=US"  + (theme != null ? "&theme=" + theme.toUpperCase() : "");
        log.info("Hitting " + uri);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + sabreAccessToken);
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            SabreDestinationResponse sabreDestinationResponse = new RestTemplate().exchange(uri, HttpMethod.GET, request, SabreDestinationResponse.class).getBody();

            if (sabreDestinationResponse != null) {
                return sabreDestinationResponse.getDestinations();
            }

            return Collections.emptyList();
        } catch (HttpClientErrorException e) {
            log.error("Failed to get top destinations", e);
            return Collections.emptyList();
        }
    }

    @Cacheable(value = "sabreDestinations", key="#theme")
    public List<Destination> getTopDestinations(String theme) {
        List<Destination> allDestinations = this.getDestinations();
        List<SabreDestinations> sabreDestinations = this.getTopSabreDestinations(StringUtils.isBlank(theme) ? null : theme);

        return sabreDestinations.stream()
                .map(sabreDestination -> allDestinations.stream()
                        .filter(destination -> destination.getCode().equals(sabreDestination.getDestination().getDestinationLocation()))
                        .findFirst()
                        .orElse(null)
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Destination> getRecentDestinations(String userId) {
        if (userId == null) {
            throw new UnauthorizedException("You must be logged in to view your recent destinations.");
        }

        return recentUserDestinationRepository.findAllByUserIdOrderByLastVisitedDateDesc(userId).stream()
                .map(recentUserDestinationEntity -> destinationMapper.map(recentUserDestinationEntity.getDestination()))
                .collect(Collectors.toList());
    }

    public List<Destination> getFavoriteDestinations(String userId) {
        if (userId == null) {
            throw new UnauthorizedException("You must be logged in to view your favorite destinations.");
        }

        return favoriteDestinationRepository.findAllByUserIdOrderByCreatedDateDesc(userId).stream()
                .map(favoriteDestinationEntity -> destinationMapper.map(favoriteDestinationEntity.getDestination()))
                .collect(Collectors.toList());
    }

    public void favoriteDestination(String slug, String userId) {
        if (userId == null) {
            throw new UnauthorizedException("You must be logged in to favorite a destination.");
        }

        DestinationEntity destinationEntity = getDestinationEntity(slug);

        FavoriteDestinationEntity existingFavoriteDestinationEntity = favoriteDestinationRepository
                .findByUserIdAndDestinationId(userId, destinationEntity.getDestinationId());
        if (existingFavoriteDestinationEntity != null) {
            throw new UnprocessableEntityException(String.format("You have already favorited %s.", destinationEntity.getName()));
        }

        FavoriteDestinationEntity favoriteDestinationEntity = FavoriteDestinationEntity.builder()
                .userId(userId)
                .destinationId(destinationEntity.getDestinationId())
                .createdDate(OffsetDateTime.now())
                .build();

        favoriteDestinationRepository.save(favoriteDestinationEntity);
    }

    public void unfavoriteDestination(String slug, String userId) {
        if (userId == null) {
            throw new UnauthorizedException("You must be logged in to unfavorite a destination.");
        }

        DestinationEntity destinationEntity = getDestinationEntity(slug);

        FavoriteDestinationEntity existingFavoriteDestinationEntity = favoriteDestinationRepository
                .findByUserIdAndDestinationId(userId, destinationEntity.getDestinationId());
        if (existingFavoriteDestinationEntity == null) {
            throw new UnprocessableEntityException(String.format("You have not favorited %s.", destinationEntity.getName()));
        }

        favoriteDestinationRepository.delete(existingFavoriteDestinationEntity);
    }

    @Cacheable(value = "yelpBusinesses", key = "{ #slug, #categories }")
    public List<YelpBusiness> getTopYelpBusinesses(String slug, String categories) {
        log.info("Getting top yelp businesses. slug: {} - categories: {}", slug, categories);
        DestinationEntity destinationEntity = getDestinationEntity(slug);
        BigDecimal latitude = destinationEntity.getLatitude();
        BigDecimal longitude = destinationEntity.getLongitude();

        final String uri = String.format(
                "https://api.yelp.com/v3/businesses/search?limit=30&sort_by=rating&latitude=%s&longitude=%s&categories=%s",
                latitude.toPlainString(),
                longitude.toPlainString(),
                categories
        );
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + yelpApiKey);
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            YelpBusinessSearchResponse yelpBusinessSearchResponse = new RestTemplate().exchange(uri, HttpMethod.GET, request, YelpBusinessSearchResponse.class).getBody();

            if (yelpBusinessSearchResponse != null) {
                return yelpBusinessSearchResponse.getBusinesses();
            }

            return Collections.emptyList();
        } catch (HttpClientErrorException e) {
            log.error(
                    "Failed to get yelp businesses. latitude: {} - longitude: {} - categories: {}",
                    latitude.toPlainString(),
                    longitude.toPlainString(),
                    categories,
                    e
            );
            return Collections.emptyList();
        }
    }
}
