package ar.edu.ubp.rest.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertiserDTO {
    Integer id;
    String agentFirstname;
    String agentLastname;
    String companyName;
    String bussinesName;
    String email;
    String phone;
    String apiUrl;
    String serviceType;
}
