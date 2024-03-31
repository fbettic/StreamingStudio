package ar.edu.ubp.rest.plataformasrest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import ar.edu.ubp.rest.plataformasrest.services.PlatformServices;

@RestController
@RequestMapping(path = "rest/plataforma", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PlataformasController {

    @Autowired
    private PlatformServices platformServices;

    @PostMapping(path = "/users")
    public ResponseEntity<PlatformUserBean> createPlatformUser(NewPlatformUserBean newPlatformUser)
            throws Exception {
        return new ResponseEntity<>(platformServices.createPlatformUser(newPlatformUser), HttpStatus.OK);
    }

    @PostMapping(path = "/associations/signup")
    public ResponseEntity<AssociationRequestBean> completeSignupAssociationRequest(
            @RequestBody CompleteSignupAssociationBean signupRequest)
            throws Exception {
        return new ResponseEntity<>(platformServices.completeSignupAssociationRequest(signupRequest),
                HttpStatus.OK);
    }

    @PostMapping(path = "/associations/login")
    public ResponseEntity<AssociationRequestBean> completeLoginAssociationRequest(
            @RequestBody CompleteLoginAssociationBean loginRequest)
            throws Exception {

        return new ResponseEntity<>(platformServices.completeLoginAssociationRequest(loginRequest),
                HttpStatus.OK);
    }

    @PostMapping(path = "/associations")
    public ResponseEntity<AssociationRequestBean> createAssociationRequest(
            @RequestBody NewAssociationRequestBean newAssociationRequest)
            throws Exception {
        return new ResponseEntity<>(platformServices.createAssociationRequest(newAssociationRequest), HttpStatus.OK);
    }

    @PostMapping(path = "/sessions")
    public ResponseEntity<SessionBean> createSession(@RequestBody NewSessionBean newSession) throws Exception {
        return new ResponseEntity<>(platformServices.createSession(newSession), HttpStatus.OK);
    }

    @PostMapping(path = "/films")
    public ResponseEntity<List<FilmBean>> getAllFilms(@RequestBody AuthTokenRequestBean authToken) throws Exception {
        return new ResponseEntity<>(platformServices.getAllFilms(authToken.getAuthToken()), HttpStatus.OK);
    }

    @PostMapping(path = "/ping")
    public ResponseEntity<String> ping(@RequestBody AuthTokenRequestBean authToken) throws Exception {
        System.out.println("---------------> " + authToken.getAuthToken());

        return new ResponseEntity<>(platformServices.ping(authToken.getAuthToken()), HttpStatus.OK);
    }
}
