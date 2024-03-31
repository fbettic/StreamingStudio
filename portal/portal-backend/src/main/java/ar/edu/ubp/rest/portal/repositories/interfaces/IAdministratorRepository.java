package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.dto.AdministratorDTO;
import ar.edu.ubp.rest.portal.models.users.Administrator;

public interface IAdministratorRepository {

    public AdministratorDTO getAdministratorByEmail(String email);

    public AdministratorDTO createAdministrator(Administrator administrator);

}
