package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.dto.SessionDTO;

public interface ISessionRepository {
    public SessionDTO createSession(SessionDTO session , String filmCode);
    public SessionDTO getSessionById(Integer id);
    public SessionDTO markSesionAsUsed(Integer id);
}
