package ar.edu.ubp.rest.plataformasrest.repositories.interfaces;

import ar.edu.ubp.rest.plataformasrest.beans.ServiceConnectionBean;

public interface IServiceConnectionRepository {
    public ServiceConnectionBean getServiceConnectionByToken(String authToken);
}
