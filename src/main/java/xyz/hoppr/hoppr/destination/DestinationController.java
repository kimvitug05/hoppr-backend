package xyz.hoppr.hoppr.destination;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://www.hoppr.xyz/", "https://www.hoppr.xyz/" })
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @GetMapping("/destinations")
    public List<Destination> getDestinations() {
        return destinationService.getDestinations();
    }

    @GetMapping("/destinations/{slug}")
    public Destination getDestination(@PathVariable String slug, Authentication authentication) {
        String userId = authentication != null ? (String) authentication.getPrincipal() : null;

        return destinationService.getDestination(slug, userId);
    }

    @GetMapping("/top-destinations")
    public List<Destination> getTopDestinations() {
        return destinationService.getTopDestinations("");
    }

    @GetMapping("/top-beach-destinations")
    public List<Destination> getTopBeachDestinations() {
        return destinationService.getTopDestinations("BEACH");
    }

    @GetMapping("/top-outdoor-destinations")
    public List<Destination> getTopOutdoorDestinations() {
        return destinationService.getTopDestinations("OUTDOORS");
    }

    @GetMapping("/recent-destinations")
    public List<Destination> getRecentDestinations(Authentication authentication) {
        String userId = authentication != null ? (String) authentication.getPrincipal() : null;

        return destinationService.getRecentDestinations(userId);
    }

    @GetMapping("/favorite-destinations")
    public List<Destination> getFavoriteDestinations(Authentication authentication) {
        String userId = authentication != null ? (String) authentication.getPrincipal() : null;

        return destinationService.getFavoriteDestinations(userId);
    }

    @PostMapping("/destinations/{slug}/favorite")
    @ResponseStatus(HttpStatus.CREATED)
    public void favoriteDestination(@PathVariable String slug, Authentication authentication) {
        String userId = authentication != null ? (String) authentication.getPrincipal() : null;

        destinationService.favoriteDestination(slug, userId);
    }

    @DeleteMapping("/destinations/{slug}/unfavorite")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfavoriteDestination(@PathVariable String slug, Authentication authentication) {
        String userId = authentication != null ? (String) authentication.getPrincipal() : null;

        destinationService.unfavoriteDestination(slug, userId);
    }

    @GetMapping("/destinations/{slug}/top-eats")
    // TODO: CACHE EVERY 24 HOURS
    public List<YelpBusiness> getTopEats(@PathVariable String slug) {
        return destinationService.getTopYelpBusinesses(slug, "food");
    }

    @GetMapping("/destinations/{slug}/top-entertainment")
    @CrossOrigin(origins = "http://localhost:3000")
    // TODO: CACHE EVERY 24 HOURS
    public List<YelpBusiness> getTopEntertainment(@PathVariable String slug) {
        return destinationService.getTopYelpBusinesses(slug, "arts");
    }

    @GetMapping("/destinations/{slug}/top-active")
    @CrossOrigin(origins = "http://localhost:3000")
    // TODO: CACHE EVERY 24 HOURS
    public List<YelpBusiness> getTopActive(@PathVariable String slug) {
        return destinationService.getTopYelpBusinesses(slug, "active");
    }
}
