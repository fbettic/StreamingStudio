package ar.edu.ubp.rest.portal.models.users;

import ar.edu.ubp.rest.portal.enums.Role;
import ar.edu.ubp.rest.portal.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Advertiser extends CustomUserDetails {
    private String companyName;
    private String bussinesName;
    private String phone;
    private String apiUrl;
    private String authToken;
    private ServiceType serviceType;

    @Builder(builderMethodName = "advertiserBuilder")
    public Advertiser(
            final Integer advertiserId,
            final String agentFirstname,
            final String agentLastname,
            final String email,
            final String password,
            final String companyName,
            final String bussinesName,
            final String phone,
            final String apiUrl,
            final String authToken,
            final ServiceType serviceType) {
        super(advertiserId, agentFirstname, agentLastname, email, password, Role.ADVERTISER);
        this.companyName = companyName;
        this.bussinesName = bussinesName;
        this.phone = phone;
        this.apiUrl = apiUrl;
        this.authToken = authToken;
        this.serviceType = serviceType;
    }

    public String getAgentFirstname() {
        return this.getFirstname();
    }

    public String getAgentLastname() {
        return this.getLastname();
    }

    public void setAgentFirstname(String firstname) {
        this.setFirstname(firstname);
    }

    public void setAgentLastname(String lastname) {
        this.setLastname(lastname);
    }
}
