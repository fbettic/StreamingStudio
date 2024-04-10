package ar.edu.ubp.rest.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreamingPlatformDTO {
    private Integer platformId;
    private String platformName;
    private String email;
    private String apiUrl;
    private String authToken;
    private Float signupFee;
    private Float loginFee;
    private String serviceType;
}
