package xyz.hoppr.hoppr.destination;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class YelpBusiness {

    private String id;
    private String alias;
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("is_closed")
    private Boolean isClosed;
    private String url;
    @JsonProperty("review_count")
    private Integer reviewCount;
    private List<YelpBusinessCategory> categories;
    private Double rating;
    private Coordinates coordinates;
    private List<String> transactions;
    private String price;
    private YelpBusinessLocation location;
    private String phone;
    @JsonProperty("display_phone")
    private String displayPhone;
    private Double distance;
}
