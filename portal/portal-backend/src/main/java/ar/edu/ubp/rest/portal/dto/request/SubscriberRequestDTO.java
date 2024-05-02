package ar.edu.ubp.rest.portal.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberRequestDTO {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String birth;
    private List<Integer> marketingPreferences;
}
