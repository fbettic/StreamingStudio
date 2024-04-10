package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.dto.AssociationDTO;


public interface IAssociationRepository {
    public AssociationDTO createAssociation(AssociationDTO association);
    public AssociationDTO getAssociationToken(Integer platformId, Integer subscriberId);
    public AssociationDTO cancelAssociation(Integer platformId, Integer subscriberId, Integer transactionId);   
}
