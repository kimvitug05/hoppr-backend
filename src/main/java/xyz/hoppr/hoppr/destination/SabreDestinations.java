package xyz.hoppr.hoppr.destination;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SabreDestinations {

    @JsonProperty("Rank")
    private Integer rank;
    @JsonProperty("Destination")
    public SabreDestination destination;
}
