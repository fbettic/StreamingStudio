package ar.edu.ubp.rest.portal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreamingPlatformSubscriberResponseDTO {
    private Integer platformId;
    private String imageUrl;
    private String platformName;
    private Boolean linked;
}
