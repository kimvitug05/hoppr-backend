package xyz.hoppr.hoppr.destination;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecentUserDestinationRepository extends JpaRepository<RecentUserDestinationEntity, Long> {

    List<RecentUserDestinationEntity> findAllByUserIdOrderByLastVisitedDateDesc(String userId);

    RecentUserDestinationEntity findByUserIdAndDestinationId(String userId, Long destinationId);
}
