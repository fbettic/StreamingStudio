package ar.edu.ubp.rest.plataformasrest.repositories.interfaces;

import ar.edu.ubp.rest.plataformasrest.beans.AssociationRequestBean;
import ar.edu.ubp.rest.plataformasrest.beans.NewAssociationRequestBean;

public interface IAssociationRequestRepository {
    public AssociationRequestBean createAssociationRequest(NewAssociationRequestBean newAssociationRequest,Integer serviceId);
    public AssociationRequestBean completeAssociationRequest(Integer userId, String uuid);
}
