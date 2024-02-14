package edu.ubp.streamingstudio.backend.streamingstudiobackend.security.beans;

import java.util.Set;

import edu.ubp.streamingstudio.backend.streamingstudiobackend.security.models.RoleEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserBean {
    
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Set<String> Roles;
}
