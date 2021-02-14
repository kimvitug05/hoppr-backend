package xyz.hoppr.hoppr.destination;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DestinationMapper {

    @Autowired
    private ObjectMapper objectMapper;

    public Destination map(DestinationEntity destinationEntity) {
        return objectMapper.convertValue(destinationEntity, Destination.class);
    }
}
