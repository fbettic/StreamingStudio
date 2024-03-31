package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;

public interface IUserRepository {
    public CustomUserDetails getUserByEmail(String email);
}
