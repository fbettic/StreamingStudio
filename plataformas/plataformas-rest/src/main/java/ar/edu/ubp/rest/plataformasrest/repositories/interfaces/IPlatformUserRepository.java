package ar.edu.ubp.rest.plataformasrest.repositories.interfaces;

import ar.edu.ubp.rest.plataformasrest.beans.NewPlatformUserBean;
import ar.edu.ubp.rest.plataformasrest.beans.PlatformUserBean;

public interface IPlatformUserRepository {
    public PlatformUserBean creatPlatformUser(NewPlatformUserBean newPlatformUser);
    public PlatformUserBean getPlatformUserByEmail(String email);
    public PlatformUserBean getPlatformUserByToken(String userToken);
}
