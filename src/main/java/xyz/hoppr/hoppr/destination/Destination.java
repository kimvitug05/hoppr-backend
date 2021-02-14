package xyz.hoppr.hoppr.destination;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Destination {

    private Long destinationId;
    private String name;
    private String slug;
    private String code;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String googlePlaceId;
    private String imageUrl;
    private String description;
}
