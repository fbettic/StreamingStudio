package ar.edu.ubp.rest.portal.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.response.AssociationResponseBean;
import ar.edu.ubp.rest.portal.beans.response.SessionResponseBean;
import ar.edu.ubp.rest.portal.dto.AssociationDTO;
import ar.edu.ubp.rest.portal.dto.AssociationRequestDTO;
import ar.edu.ubp.rest.portal.dto.SessionDTO;
import ar.edu.ubp.rest.portal.dto.request.NewAssociationRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SessionRequestDTO;
import ar.edu.ubp.rest.portal.repositories.AssociationRepository;
import ar.edu.ubp.rest.portal.repositories.AssociationRequestRepository;
import ar.edu.ubp.rest.portal.repositories.SessionRepository;
import ar.edu.ubp.rest.portal.utils.AuthUrlGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssociationService {

    private SessionRepository sessionRepository;
    @Autowired
    private AssociationRepository associationRepository;
    @Autowired
    private AssociationRequestRepository associationRequestRepository;

    // Services
    @Autowired
    private PlatformApiClientService platformApiClientService;
    

    // Association management
    public AssociationRequestDTO createAssociationRequest(NewAssociationRequestDTO newAssociationRequest) {

        String uuid = AuthUrlGenerator.generateUuid();
        String redirectUrl = AuthUrlGenerator.generateAuthUrl(newAssociationRequest.getAssociationType(), uuid);

        AssociationResponseBean response = platformApiClientService.createAssociationRequest(newAssociationRequest,
                redirectUrl);

        AssociationRequestDTO associationRequest = AssociationRequestDTO.builder()
                .platformId(newAssociationRequest.getPlatformId())
                .transactionId(response.getAssociationId())
                .subscriberId(newAssociationRequest.getSubscriberId())
                .state(response.getState())
                .uuid(uuid)
                .authUrl(response.getRedirectUrl())
                .associationType(response.getAssociationType())
                .requestAt(response.getRequestedAt())
                .build();

        return associationRequestRepository.createAssociationRequest(associationRequest);
    }

    public AssociationDTO getAssociationData(String uuid) {
        AssociationRequestDTO associationRequest = associationRequestRepository.getAssociationRequestByUuid(uuid);

        AssociationResponseBean response = platformApiClientService
                .getAssociationData(associationRequest.getPlatformId(), associationRequest.getTransactionId());

        AssociationDTO association = AssociationDTO.builder()
                .transactionId(associationRequest.getTransactionId())
                .platformId(associationRequest.getPlatformId())
                .subscriberId(associationRequest.getSubscriberId())
                .userToken(response.getUserToken())
                .entryDate(new Date())
                .build();

        return associationRepository.createAssociation(association);
    }

    public SessionDTO createSession(SessionRequestDTO sessionRequest) {
        SessionResponseBean response = platformApiClientService.createSession(sessionRequest);

        SessionDTO session = SessionDTO.builder()
                .subscriberId(sessionRequest.getSubscriberId())
                .platformId(sessionRequest.getPlatformId())
                .transactionId(response.getAssociationId())
                .filmCode(sessionRequest.getFilmCode())
                .sessionUrl(response.getSessionUrl())
                .createdAt(new Date())
                .build();

        return sessionRepository.createSession(session);
    }

}
