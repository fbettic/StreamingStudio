package ar.edu.ubp.rest.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertiserSignupRequest {
    String agentName;
    String agentLastname;
    String companyName;
    String bussinesName;
    String email;
    String phone;
    String password;
    String apiUrl;
    String authToken;    
    String serviceType;
}
