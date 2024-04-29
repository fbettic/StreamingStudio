package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.dto.AssociationRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.NewAssociationRequestDTO;

public interface IAssociationRequestRepository {
    public AssociationRequestDTO createAssociationRequest(AssociationRequestDTO associationRequest);
    public AssociationRequestDTO getLastOpenAssociationRequest(NewAssociationRequestDTO association);
    public AssociationRequestDTO getAssociationRequestByUuid(String uuid);
    public AssociationRequestDTO cancelAssociationRequest(Integer platformId, Integer subscriberId, Integer transactionId);
    
}
