package ar.edu.ubp.rest.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreamingPlatformRequestDTO {
    private String platformName;
    private String email;
    private String apiUrl;
    private String imageUrl;
    private String authToken;
    private Integer signupFeeId;
    private Integer loginFeeId;
    private String serviceType;
}
