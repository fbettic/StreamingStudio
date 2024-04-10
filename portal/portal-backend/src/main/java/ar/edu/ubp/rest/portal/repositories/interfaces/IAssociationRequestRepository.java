package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.dto.AssociationRequestDTO;

public interface IAssociationRequestRepository {
    public AssociationRequestDTO createAssociationRequest(AssociationRequestDTO associationRequest);
    public AssociationRequestDTO getLastOpenAssociationRequest(Integer platformId, Integer subscriberId);
    public AssociationRequestDTO getAssociationRequestByUuid(String uuid);
    public AssociationRequestDTO cancelAssociationRequest(Integer platformId, Integer subscriberId, Integer transactionId);
    
}
