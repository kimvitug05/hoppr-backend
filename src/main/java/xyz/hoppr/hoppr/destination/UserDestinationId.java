package xyz.hoppr.hoppr.destination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDestinationId implements Serializable {

    private String userId;
    private Long destinationId;
}
