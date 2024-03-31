package ar.edu.ubp.soap.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPlatformUserBean {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
}
