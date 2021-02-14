package xyz.hoppr.hoppr.destination;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SabreDestinationResponse {

    @JsonProperty("OriginLocation")
    private String originLocation;
    @JsonProperty("Destinations")
    private List<SabreDestinations> destinations;
    @JsonProperty("LookBackWeeks")
    private Integer lookBackWeeks;
    @JsonProperty("Links")
    private List<SabreLink> links;
}
