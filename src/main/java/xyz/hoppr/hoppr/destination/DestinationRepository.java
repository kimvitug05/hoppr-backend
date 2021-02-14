package xyz.hoppr.hoppr.destination;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DestinationRepository extends JpaRepository<DestinationEntity, Long> {

    Optional<DestinationEntity> findBySlug(String slug);
}
