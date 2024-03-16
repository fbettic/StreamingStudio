package ar.edu.ubp.rest.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorDTO {
    Integer id;
    String email;
    String firstname;
    String lastname;
}
