package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.dto.SessionDTO;

public interface ISessionRepository {
    public SessionDTO createSession(SessionDTO session);
    public SessionDTO markSesionAsUsed(Integer id);
}
