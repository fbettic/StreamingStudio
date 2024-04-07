package ar.edu.ubp.rest.plataformasrest.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.plataformasrest.beans.AssociationRequestBean;
import ar.edu.ubp.rest.plataformasrest.beans.AuthTokenRequestBean;
import ar.edu.ubp.rest.plataformasrest.beans.CompleteLoginAssociationBean;
import ar.edu.ubp.rest.plataformasrest.beans.CompleteSignupAssociationBean;
import ar.edu.ubp.rest.plataformasrest.beans.FilmBean;
import ar.edu.ubp.rest.plataformasrest.beans.NewAssociationRequestBean;
import ar.edu.ubp.rest.plataformasrest.beans.NewPlatformUserBean;
import ar.edu.ubp.rest.plataformasrest.beans.NewSessionBean;
import ar.edu.ubp.rest.plataformasrest.beans.PlatformUserBean;
import ar.edu.ubp.rest.plataformasrest.beans.SessionBean;
import ar.edu.ubp.rest.plataformasrest.repositories.AssociationRequestRepository;
import ar.edu.ubp.rest.plataformasrest.repositories.FilmRepository;
import ar.edu.ubp.rest.plataformasrest.repositories.PlatformUserRepository;
import ar.edu.ubp.rest.plataformasrest.repositories.ServiceConnectionRepository;
import ar.edu.ubp.rest.plataformasrest.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlatformServices {
    private final AssociationRequestRepository associationRequestRepository;
    private final FilmRepository filmRepository;
    private final PlatformUserRepository platformUserRepository;
    private final ServiceConnectionRepository serviceConnectionRepository;
    private final SessionRepository sessionRepository;

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

        System.out.println("------>" + response);
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

    public AssociationRequestBean getAssociationData(Integer id, AuthTokenRequestBean authToken) throws Exception{
        final Integer serviceId = serviceConnectionRepository
                .getServiceConnectionByToken(authToken.getAuthToken()).getServiceId();
        if (serviceId == null || serviceId == 0) {
            throw new Exception("Invalid token");
        }
        return associationRequestRepository.getAssociationData(id);
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

    public List<FilmBean> getAllFilms(String authToken) throws Exception {
        final Integer serviceId = serviceConnectionRepository.getServiceConnectionByToken(authToken).getServiceId();

        if (serviceId == null) {
            throw new Exception("Invalid token");
        }

        return filmRepository.getAllFilms();
    }

    public String ping(String authToken) throws Exception {
        Integer serviceId = serviceConnectionRepository.getServiceConnectionByToken(authToken).getServiceId();
        if (serviceId == null || serviceId == 0) {
            throw new Exception("Invalid token");
        }
        return "pong";
    }
}
