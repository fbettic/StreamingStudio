package ar.edu.ubp.soap.ws;

import java.util.List;
import java.util.Objects;

import org.apache.cxf.interceptor.Fault;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.soap.beans.*;
import ar.edu.ubp.soap.db.AuthManager;
import ar.edu.ubp.soap.db.FilmManager;
import ar.edu.ubp.soap.db.ReportManager;
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
    private ReportManager reportManager;

    public PlataformasWS() {
        this.authManager = new AuthManager();
        this.userManager = new UserManager();
        this.filmManager = new FilmManager();
        this.reportManager = new ReportManager();
    }

    // crear usuario no relacionado a plataforma
    @WebMethod()
    @WebResult(name = "platformUser")
    public PlatformUserBean createPlatformUser(@WebParam(name = "newPlatformUser") NewPlatformUserBean newPlatformUser) {
        try {
            return userManager.creatPlatformUser(newPlatformUser);
        } catch (Exception e) {
            throw new Fault(new Exception("Error creating platform user: " + e.getMessage()));
        }
    }

    // se crea el usuario y se setea el token
    @WebMethod()
    @WebResult(name = "signupAssociationCompleted")
    public AssociationRequestBean completeSignupAssociationRequest(
            @WebParam(name = "newPlatformUser") NewPlatformUserBean newPlatformUser,
            @WebParam(name = "uuid") String uuid) {
        try {
            Integer userId = userManager.creatPlatformUser(newPlatformUser).getUserId();
            return userManager.completeAssociationRequest(userId, uuid);
        } catch (Exception e) {
            throw new Fault(new Exception("Error completing signup association request: " + e.getMessage()));
        }
    }

    // se busca el usuario y se setea el token
    @WebMethod()
    @WebResult(name = "loginAssociationCompleted")
    public AssociationRequestBean completeLoginAssociationRequest(
            @WebParam(name = "loginRequest") LoginRequestBean loginRequest, @WebParam(name = "uuid") String uuid) {
        try {
            PlatformUserBean user = userManager.getUserByEmail(loginRequest.getEmail());

            if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
                throw new Fault(new Exception("Invalid user credentials"));
            }

            return userManager.completeAssociationRequest(user.getUserId(), uuid);
        } catch (Exception e) {
            throw new Fault(new Exception("Error completing login association request: " + e.getMessage()));
        }
    }

    // se abre el proceso de associacion
    @WebMethod()
    @WebResult(name = "associationRequest")
    public AssociationRequestBean getAssociationData(
            @WebParam(name = "authToken") String authToken, @WebParam(name = "associationId") Integer associationId) {
        try {
            final Integer serviceId = authManager.validateServiceToken(authToken);
            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }
            return userManager.getAssociationData(associationId);
        } catch (Exception e) {
            throw new Fault(new Exception("Error getting association data: " + e.getMessage()));
        }
    }

    @WebMethod()
    @WebResult(name = "associationRequest")
    public AssociationRequestBean cancelAssociationRequest(
            @WebParam(name = "authToken") String authToken,
            @WebParam(name = "userToken") String userToken) {
        try {
            final Integer serviceId = authManager.validateServiceToken(authToken);
            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }

            final AssociationRequestBean association = userManager.getAssociationRequestByToken(userToken);

            if (!Objects.isNull(association)
                    && (association.getState().equals("CANCELED")
                    || !Objects.isNull(association.getCanceledAt()))) {
                return association;
            }
            return userManager.cancelAssociationRequest(userToken);
        } catch (Exception e) {
            throw new Fault(new Exception("Error canceling association request: " + e.getMessage()));
        }
    }

    @WebMethod()
    @WebResult(name = "associationRequest")
    public AssociationRequestBean createAssociationRequest(
            @WebParam(name = "newAssociationRequest") NewAssociationRequestBean newAssociationRequest) {
        try {
            final Integer serviceId = authManager.validateServiceToken(newAssociationRequest.getAuthToken());
            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }
            return userManager.createAssociationRequest(newAssociationRequest, serviceId);
        } catch (Exception e) {
            throw new Fault(new Exception("Error creating association request: " + e.getMessage()));
        }
    }

    // se obtienen los datos de reproduccion de la pelicula
    @WebMethod()
    @WebResult(name = "session")
    public SessionBean createSession(@WebParam(name = "newSession") NewSessionBean newSession) {
        try {
            final Integer serviceId = authManager.validateServiceToken(newSession.getAuthToken());
            final Integer userId = authManager.validateUserToken(newSession.getUserToken());
            return userManager.createSession(newSession, serviceId, userId);
        } catch (Exception e) {
            throw new Fault(new Exception("Error creating session: " + e.getMessage()));
        }
    }

    @WebMethod()
    @WebResult(name = "session")
    public SessionBean markSessionAsUsed(@WebParam(name = "sessionId") Integer sessionId,
                                         @WebParam(name = "authToken") String authToken) {
        try {
            final Integer serviceId = authManager.validateServiceToken(authToken);
            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }
            return userManager.markSessionAsUsed(sessionId);
        } catch (Exception e) {
            throw new Fault(new Exception("Error marking session as used: " + e.getMessage()));
        }
    }

    @WebMethod()
    @WebResult(name = "session")
    public SessionBean markSessionAsExpired(@WebParam(name = "sessionId") Integer sessionId,
                                            @WebParam(name = "authToken") String authToken) {
        try {
            final Integer serviceId = authManager.validateServiceToken(authToken);
            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }
            return userManager.markSessionAsExpired(sessionId);
        } catch (Exception e) {
            throw new Fault(new Exception("Error marking session as expired: " + e.getMessage()));
        }
    }

    // obtener todas las peliculas
    @WebMethod()
    @WebResult(name = "films")
    public List<FilmBean> getAllFilms(@WebParam(name = "authToken") String authToken) {
        try {
            authManager.validateServiceToken(authToken);
            return filmManager.getAllFilms();
        } catch (Exception e) {
            throw new Fault(new Exception("Error getting all films: " + e.getMessage()));
        }
    }

    @WebMethod()
    @WebResult(name = "report")
    public BasicResponseBean receiveWeeklyReport(@WebParam(name = "report") WeeklyReportBean report) {
        try {
            final Integer serviceId = authManager.validateServiceToken(report.getAuthToken());

            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(report);

            return new BasicResponseBean(reportManager.createWeeklyReport(jsonString));
        } catch (JsonProcessingException e) {
            throw new Fault(new Exception("Error processing weekly report: " + e.getMessage()));
        } catch (Exception e) {
            throw new Fault(new Exception("Error receiving weekly report: " + e.getMessage()));
        }
    }

    @WebMethod()
    @WebResult(name = "pong")
    public BasicResponseBean ping(@WebParam(name = "authToken") String authToken) {
        try {
            Integer serviceId = authManager.validateServiceToken(authToken);
            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }
            return new BasicResponseBean("pong");
        } catch (Exception e) {
            throw new Fault(new Exception("Error pinging service: " + e.getMessage()));
        }
    }
}
