package ar.edu.ubp.rest.plataformasrest.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.rest.plataformasrest.beans.AssociationRequestBean;
import ar.edu.ubp.rest.plataformasrest.beans.AuthTokenRequestBean;
import ar.edu.ubp.rest.plataformasrest.beans.BasicResponseBean;
import ar.edu.ubp.rest.plataformasrest.beans.CompleteLoginAssociationBean;
import ar.edu.ubp.rest.plataformasrest.beans.CompleteSignupAssociationBean;
import ar.edu.ubp.rest.plataformasrest.beans.FilmBean;
import ar.edu.ubp.rest.plataformasrest.beans.NewAssociationRequestBean;
import ar.edu.ubp.rest.plataformasrest.beans.NewPlatformUserBean;
import ar.edu.ubp.rest.plataformasrest.beans.NewSessionBean;
import ar.edu.ubp.rest.plataformasrest.beans.PlatformUserBean;
import ar.edu.ubp.rest.plataformasrest.beans.SessionBean;
import ar.edu.ubp.rest.plataformasrest.beans.UserRequest;
import ar.edu.ubp.rest.plataformasrest.beans.WeeklyReportBean;
import ar.edu.ubp.rest.plataformasrest.repositories.AssociationRequestRepository;
import ar.edu.ubp.rest.plataformasrest.repositories.FilmRepository;
import ar.edu.ubp.rest.plataformasrest.repositories.PlatformUserRepository;
import ar.edu.ubp.rest.plataformasrest.repositories.ReportRepository;
import ar.edu.ubp.rest.plataformasrest.repositories.ServiceConnectionRepository;
import ar.edu.ubp.rest.plataformasrest.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlatformServices {
    @Autowired
    private final AssociationRequestRepository associationRequestRepository;

    @Autowired
    private final FilmRepository filmRepository;

    @Autowired
    private final PlatformUserRepository platformUserRepository;

    @Autowired
    private final ServiceConnectionRepository serviceConnectionRepository;

    @Autowired
    private final SessionRepository sessionRepository;

    @Autowired
    private final ReportRepository reportRepository;

    public PlatformUserBean createPlatformUser(NewPlatformUserBean newPlatformUser) throws Exception {
        return platformUserRepository.creatPlatformUser(newPlatformUser);
    }

    public AssociationRequestBean completeSignupAssociationRequest(CompleteSignupAssociationBean signupRequest)
            throws Exception {
        NewPlatformUserBean newUser = NewPlatformUserBean.builder()
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .firstname(signupRequest.getFirstname())
                .lastname(signupRequest.getLastname())
                .phone(signupRequest.getPhone())
                .build();

        Integer userId = platformUserRepository.creatPlatformUser(newUser).getUserId();

        AssociationRequestBean response = associationRequestRepository.completeAssociationRequest(userId,
                signupRequest.getUuid());

        return response;
    }

    public AssociationRequestBean completeLoginAssociationRequest(CompleteLoginAssociationBean loginRequest)
            throws Exception {

        PlatformUserBean user = platformUserRepository.getPlatformUserByEmail(loginRequest.getEmail());
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            throw new Exception("Invalid user credentials");
        }
        AssociationRequestBean response = associationRequestRepository.completeAssociationRequest(user.getUserId(),
                loginRequest.getUuid());

        return response;
    }

    public AssociationRequestBean createAssociationRequest(NewAssociationRequestBean newAssociationRequest)
            throws Exception {
        final Integer serviceId = serviceConnectionRepository
                .getServiceConnectionByToken(newAssociationRequest.getAuthToken()).getServiceId();
        if (serviceId == null || serviceId == 0) {
            throw new Exception("Invalid token");
        }
        return associationRequestRepository.createAssociationRequest(newAssociationRequest, serviceId);
    }

    public AssociationRequestBean getAssociationData(Integer id, AuthTokenRequestBean authToken) throws Exception {
        final Integer serviceId = serviceConnectionRepository
                .getServiceConnectionByToken(authToken.getAuthToken()).getServiceId();

        if (serviceId == null || serviceId == 0) {
            throw new Exception("Invalid token");
        }
        return associationRequestRepository.getAssociationData(id);
    }

    public AssociationRequestBean cancelAssociationRequest(UserRequest request)
            throws Exception {
        final Integer serviceId = serviceConnectionRepository
                .getServiceConnectionByToken(request.getAuthToken()).getServiceId();

        final Integer userId = platformUserRepository.getPlatformUserByToken(request.getUserToken()).getUserId();

        if (serviceId == null || serviceId == 0 || userId == null) {
            throw new Exception("Invalid token");
        }

        final AssociationRequestBean association = associationRequestRepository
                .getAssociationRequestByToken(request.getUserToken());


        if (!Objects.isNull(association)
                && (association.getState().equals("CANCELED")
                        || !Objects.isNull(association.getCanceledAt()))) {
            return association;
        }

        return associationRequestRepository.cancelAssociationRequest(request.getUserToken());
    }

    public SessionBean createSession(NewSessionBean newSession) throws Exception {
        final Integer serviceId = serviceConnectionRepository.getServiceConnectionByToken(newSession.getAuthToken())
                .getServiceId();
        final Integer userId = platformUserRepository.getPlatformUserByToken(newSession.getUserToken()).getUserId();

        if (serviceId == null || userId == null) {
            throw new Exception("Invalid token");
        }

        return sessionRepository.createSession(newSession, serviceId, userId);
    }

    public SessionBean markSessionAsUsed(Integer id, AuthTokenRequestBean authToken) throws Exception {
        final Integer serviceId = serviceConnectionRepository.getServiceConnectionByToken(authToken.getAuthToken())
                .getServiceId();

        if (serviceId == null) {
            throw new Exception("Invalid token");
        }

        return sessionRepository.markSessionAsUsed(id);
    }

    public SessionBean markSessionAsExpired(Integer id, AuthTokenRequestBean authToken) throws Exception {
        final Integer serviceId = serviceConnectionRepository.getServiceConnectionByToken(authToken.getAuthToken())
                .getServiceId();

        if (serviceId == null) {
            throw new Exception("Invalid token");
        }

        return sessionRepository.markSessionAsExpired(id);
    }

    public List<FilmBean> getAllFilms(String authToken) throws Exception {
        final Integer serviceId = serviceConnectionRepository.getServiceConnectionByToken(authToken).getServiceId();

        if (serviceId == null) {
            throw new Exception("Invalid token");
        }

        return filmRepository.getAllFilms();
    }

    public BasicResponseBean createWeeklyReport(WeeklyReportBean report) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(report);
        } catch (JsonProcessingException e) {
            throw e;
        }
        return new BasicResponseBean(reportRepository.createWeekleyReport(jsonString));
    }

    public BasicResponseBean ping(String authToken) throws Exception {
        Integer serviceId = serviceConnectionRepository.getServiceConnectionByToken(authToken).getServiceId();
        if (serviceId == null || serviceId == 0) {
            throw new Exception("Invalid token");
        }
        return new BasicResponseBean("pong");
    }
}
