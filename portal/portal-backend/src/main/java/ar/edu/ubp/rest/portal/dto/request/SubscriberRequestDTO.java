package ar.edu.ubp.rest.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberRequestDTO {
    String email;
    String password;
    String firstname;
    String lastname;
    String phone;
    String birth;
}
