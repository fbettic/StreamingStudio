package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.dto.AdministratorDTO;
import ar.edu.ubp.rest.portal.models.Administrator;

public interface IAdministratorRepository {

    public AdministratorDTO findAdministratorByEmail(String email);

    public AdministratorDTO save(Administrator administrator);

}
