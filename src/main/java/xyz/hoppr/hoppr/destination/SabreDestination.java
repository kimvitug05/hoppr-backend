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
public class SabreDestination {

    @JsonProperty("DestinationLocation")
    private String destinationLocation;
    @JsonProperty("AirportName")
    private String airportName;
    @JsonProperty("CityName")
    private String cityName;
    @JsonProperty("CountryCode")
    private String countryCode;
    @JsonProperty("CountryName")
    private String countryName;
    @JsonProperty("RegionName")
    private String regionName;
    @JsonProperty("MetropolitanAreaName")
    private String metropolitanAreaName;
    @JsonProperty("Links")
    private List<SabreLink> links;
    @JsonProperty("Type")
    private String type;
}
