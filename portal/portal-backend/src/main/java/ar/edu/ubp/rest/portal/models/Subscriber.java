package ar.edu.ubp.rest.portal.models;

import java.time.LocalDate;

import ar.edu.ubp.rest.portal.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Subscriber extends CustomUserDetails {
    private String phone;
    private Boolean validated;
    private LocalDate birth;

    @Builder(builderMethodName = "subscriberBuilder")
    public Subscriber(
            final Integer subscriberId,
            final String firstname,
            final String lastname,
            final String email,
            final String password,
            final String phone,
            final boolean validated,
            final LocalDate birth) {
        super(subscriberId, firstname, lastname, email, password, Role.SUBSCRIBER);
        this.birth = birth;
        this.validated = validated;
        this.phone = phone;
    }

    public Integer getValidated() {
        return validated ? 1 : 0;
    }
}
