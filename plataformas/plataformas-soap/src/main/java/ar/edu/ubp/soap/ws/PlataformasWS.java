package ar.edu.ubp.soap.ws;

import java.util.List;

import org.apache.cxf.interceptor.Fault;

import ar.edu.ubp.soap.beans.AssociationRequestBean;
import ar.edu.ubp.soap.beans.FilmBean;
import ar.edu.ubp.soap.beans.LoginRequestBean;
import ar.edu.ubp.soap.beans.NewAssociationRequestBean;
import ar.edu.ubp.soap.beans.NewPlatformUserBean;
import ar.edu.ubp.soap.beans.NewSessionBean;
import ar.edu.ubp.soap.beans.PlatformUserBean;
import ar.edu.ubp.soap.beans.SessionBean;
import ar.edu.ubp.soap.db.AuthManager;
import ar.edu.ubp.soap.db.DatabaseConnection;
import ar.edu.ubp.soap.db.FilmManager;
import ar.edu.ubp.soap.db.UserManager;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@WebService
@XmlSeeAlso({})
public class PlataformasWS {

    private AuthManager authManager;
    private UserManager userManager;
    private FilmManager filmManager;

    public PlataformasWS() {
        this.authManager = new AuthManager();
        this.userManager = new UserManager();
        this.filmManager = new FilmManager();
    }    

    // crear usuario no relacionado a plataforma
    @WebMethod()
    @WebResult(name = "platformUser")
    public PlatformUserBean createPlatformUser(@WebParam(name = "newPlatformUser") NewPlatformUserBean newPlatformUser) throws Exception {
        return userManager.creatPlatformUser(newPlatformUser);
    }

    // se crea el usuario y se setea el token
    @WebMethod()
    @WebResult(name = "signupAssociationCompleted")
    public AssociationRequestBean completeSignupAssociationRequest(@WebParam(name = "newPlatformUser") NewPlatformUserBean newPlatformUser, @WebParam(name = "uuid") String uuid) throws Exception {
        Integer userId = userManager.creatPlatformUser(newPlatformUser).getUserId();

        System.out.println("-------------> " + userId);

        AssociationRequestBean response = userManager.completeAssociationRequest(userId, uuid);

        System.out.println("-------------> " + response);

        return response;
    }

    // se busca el usuario y se setea el token
    @WebMethod()
    @WebResult(name = "loginAssociationCompleted")
    public AssociationRequestBean completeLoginAssociationRequest(@WebParam(name = "loginRequest") LoginRequestBean loginRequest, @WebParam(name = "uuid") String uuid) throws Exception {
        
        System.out.println("-------------> " + loginRequest.toString() + ", " + uuid);
        PlatformUserBean user = userManager.getUserByEmail(loginRequest.getEmail());
        

        if(user == null || !user.getPassword().equals(loginRequest.getPassword()))
        {
            throw new Fault(new Exception("Invalid user credentials"));
        }

        System.out.println("-------------> " + user.toString());

        AssociationRequestBean response = userManager.completeAssociationRequest(user.getUserId(), uuid);

        System.out.println("-------------> " + response);

        return response;
    }

    // se abre el proceso de associacion
    @WebMethod()
    @WebResult(name = "associationRequest")
    public AssociationRequestBean getAssociationData(
        @WebParam(name = "authToken") String authToken, @WebParam(name = "associationId") Integer associationId) throws Exception {
        final Integer serviceId = authManager.validateServiceToken(authToken);
        if (serviceId == null || serviceId == 0) {
            throw new Fault(new Exception("Invalid token"));
        }
        return userManager.getAssociationData(associationId);
    }

    @WebMethod()
    @WebResult(name = "associationRequest")
    public AssociationRequestBean createAssociationRequest(
            @WebParam(name = "newAssociationRequest") NewAssociationRequestBean newAssociationRequest) throws Exception {
        final Integer serviceId = authManager.validateServiceToken(newAssociationRequest.getAuthToken());
        if (serviceId == null || serviceId == 0) {
            throw new Fault(new Exception("Invalid token"));
        }
        return userManager.createAssociationRequest(newAssociationRequest, serviceId);
    }


    // se obtienen los datos de reproduccion de la pelicula
    @WebMethod()
    @WebResult(name = "session")
    public SessionBean createSession(@WebParam(name = "newSession") NewSessionBean newSession) throws Exception {
        final Integer serviceId = authManager.validateServiceToken(newSession.getAuthToken());
        final Integer userId = authManager.validateUserToken(newSession.getUserToken());
        return userManager.createSession(newSession, serviceId, userId);
    }

    // obtener todas las peliculas
    @WebMethod()
    @WebResult(name = "films")
    public List<FilmBean> getAllFilms(@WebParam(name = "authToken") String authToken) throws Fault {
        authManager.validateServiceToken(authToken);
        return filmManager.getAllFilms();
    }

    // TODO añadir logica de almacenamiento de reporte

    @WebMethod()
    @WebResult(name = "pong")
    public String ping(@WebParam(name = "authToken") String authToken) throws Fault {
        Integer serviceId = authManager.validateServiceToken(authToken);
        if (serviceId == null || serviceId == 0) {
            throw new Fault(new Exception("Invalid token"));
        }
        return "pong";
    }
}
