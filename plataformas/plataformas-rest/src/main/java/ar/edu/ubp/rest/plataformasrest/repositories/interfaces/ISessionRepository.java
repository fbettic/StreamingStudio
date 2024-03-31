package ar.edu.ubp.rest.plataformasrest.repositories.interfaces;

import ar.edu.ubp.rest.plataformasrest.beans.NewSessionBean;
import ar.edu.ubp.rest.plataformasrest.beans.SessionBean;

public interface ISessionRepository {
    public SessionBean createSession(NewSessionBean newSession, Integer serviceId, Integer userId);
}
