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
public class YelpBusinessLocation {

    private String address1;
    private String address2;
    private String address3;
    private String city;
    @JsonProperty("zip_code")
    private String zipCode;
    private String country;
    private String state;
    @JsonProperty("display_address")
    private List<String> displayAddress;
}
