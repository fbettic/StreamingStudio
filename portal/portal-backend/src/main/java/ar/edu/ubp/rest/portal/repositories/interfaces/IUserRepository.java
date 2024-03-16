package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.models.CustomUserDetails;

public interface IUserRepository {
    public CustomUserDetails findUserByEmail(String email);
}
