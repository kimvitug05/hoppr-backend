package xyz.hoppr.hoppr.destination;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteDestinationRepository extends JpaRepository<FavoriteDestinationEntity, Long> {

    List<FavoriteDestinationEntity> findAllByUserIdOrderByCreatedDateDesc(String userId);

    FavoriteDestinationEntity findByUserIdAndDestinationId(String userId, Long destinationId);
}
