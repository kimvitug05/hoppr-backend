package xyz.hoppr.hoppr.destination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserDestinationId.class)
@Table(name = "recent_user_destinations")
public class RecentUserDestinationEntity {

    @Id
    @Column
    private String userId;

    @Id
    @Column(name = "destination_id")
    private Long destinationId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", insertable = false, nullable = false, updatable = false)
    private DestinationEntity destination;

    @Column
    @LastModifiedDate
    private OffsetDateTime lastVisitedDate;
}
