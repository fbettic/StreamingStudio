package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.AssociationDTO;
import ar.edu.ubp.rest.portal.dto.request.ObvservationRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.MessageResponseDTO;

public interface IAssociationRepository {
    public AssociationDTO createAssociation(AssociationDTO association);
    public AssociationDTO getAssociationToken(Integer platformId, Integer subscriberId);
    public AssociationDTO cancelAssociation(Integer platformId, Integer subscriberId, Integer transactionId);  
    public List<AssociationDTO> getAllAssociationsBySubscriber(Integer subscriberId);
    public MessageResponseDTO updateObvservation(ObvservationRequestDTO request);
}
