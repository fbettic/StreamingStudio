package ar.edu.ubp.rest.portal.models.users;

import ar.edu.ubp.rest.portal.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Administrator extends CustomUserDetails {
    @Builder(builderMethodName = "administratorBuilder")
    public Administrator(
            final Integer administratorId,
            final String firstname,
            final String lastname,
            final String email,
            final String password) {
        super(administratorId, firstname, lastname, email, password, Role.ADMINISTRATOR);

    }
}
