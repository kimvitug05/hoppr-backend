package xyz.hoppr.hoppr.destination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "destinations")
public class DestinationEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destinationId;

    @Column
    private String name;

    @Column
    private String slug;

    @Column
    private String code;

    @Column
    private BigDecimal latitude;

    @Column
    private BigDecimal longitude;

    @Column
    private String googlePlaceId;

    @Column
    private String imageUrl;

    @Column
    private String description;
}
