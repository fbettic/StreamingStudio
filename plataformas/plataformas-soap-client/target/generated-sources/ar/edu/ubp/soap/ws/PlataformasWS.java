
package ar.edu.ubp.soap.ws;

import java.util.List;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.1
 * Generated source version: 3.0
 * 
 */
@WebService(name = "PlataformasWS", targetNamespace = "http://ws.soap.ubp.edu.ar/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PlataformasWS {


    /**
     * 
     * @param loginRequest
     * @param uuid
     * @return
     *     returns ar.edu.ubp.soap.ws.AssociationRequestBean
     */
    @WebMethod
    @WebResult(name = "loginAssociationCompleted", targetNamespace = "")
    @RequestWrapper(localName = "completeLoginAssociationRequest", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CompleteLoginAssociationRequest")
    @ResponseWrapper(localName = "completeLoginAssociationRequestResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CompleteLoginAssociationRequestResponse")
    public AssociationRequestBean completeLoginAssociationRequest(
        @WebParam(name = "loginRequest", targetNamespace = "")
        LoginRequestBean loginRequest,
        @WebParam(name = "uuid", targetNamespace = "")
        String uuid);

    /**
     * 
     * @param newPlatformUser
     * @return
     *     returns ar.edu.ubp.soap.ws.PlatformUserBean
     */
    @WebMethod
    @WebResult(name = "platformUser", targetNamespace = "")
    @RequestWrapper(localName = "createPlatformUser", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CreatePlatformUser")
    @ResponseWrapper(localName = "createPlatformUserResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CreatePlatformUserResponse")
    public PlatformUserBean createPlatformUser(
        @WebParam(name = "newPlatformUser", targetNamespace = "")
        NewPlatformUserBean newPlatformUser);

    /**
     * 
     * @param authToken
     * @param sessionId
     * @return
     *     returns ar.edu.ubp.soap.ws.SessionBean
     */
    @WebMethod
    @WebResult(name = "session", targetNamespace = "")
    @RequestWrapper(localName = "markSessionAsExpired", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.MarkSessionAsExpired")
    @ResponseWrapper(localName = "markSessionAsExpiredResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.MarkSessionAsExpiredResponse")
    public SessionBean markSessionAsExpired(
        @WebParam(name = "sessionId", targetNamespace = "")
        Integer sessionId,
        @WebParam(name = "authToken", targetNamespace = "")
        String authToken);

    /**
     * 
     * @param report
     * @return
     *     returns ar.edu.ubp.soap.ws.BasicResponseBean
     */
    @WebMethod
    @WebResult(name = "report", targetNamespace = "")
    @RequestWrapper(localName = "receiveWeeklyReport", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.ReceiveWeeklyReport")
    @ResponseWrapper(localName = "receiveWeeklyReportResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.ReceiveWeeklyReportResponse")
    public BasicResponseBean receiveWeeklyReport(
        @WebParam(name = "report", targetNamespace = "")
        WeeklyReportBean report);

    /**
     * 
     * @param associationId
     * @param authToken
     * @return
     *     returns ar.edu.ubp.soap.ws.AssociationRequestBean
     */
    @WebMethod
    @WebResult(name = "associationRequest", targetNamespace = "")
    @RequestWrapper(localName = "getAssociationData", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.GetAssociationData")
    @ResponseWrapper(localName = "getAssociationDataResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.GetAssociationDataResponse")
    public AssociationRequestBean getAssociationData(
        @WebParam(name = "authToken", targetNamespace = "")
        String authToken,
        @WebParam(name = "associationId", targetNamespace = "")
        Integer associationId);

    /**
     * 
     * @param authToken
     * @return
     *     returns java.util.List<ar.edu.ubp.soap.ws.FilmBean>
     */
    @WebMethod
    @WebResult(name = "films", targetNamespace = "")
    @RequestWrapper(localName = "getAllFilms", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.GetAllFilms")
    @ResponseWrapper(localName = "getAllFilmsResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.GetAllFilmsResponse")
    public List<FilmBean> getAllFilms(
        @WebParam(name = "authToken", targetNamespace = "")
        String authToken);

    /**
     * 
     * @param authToken
     * @return
     *     returns ar.edu.ubp.soap.ws.BasicResponseBean
     */
    @WebMethod
    @WebResult(name = "pong", targetNamespace = "")
    @RequestWrapper(localName = "ping", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.Ping")
    @ResponseWrapper(localName = "pingResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.PingResponse")
    public BasicResponseBean ping(
        @WebParam(name = "authToken", targetNamespace = "")
        String authToken);

    /**
     * 
     * @param newAssociationRequest
     * @return
     *     returns ar.edu.ubp.soap.ws.AssociationRequestBean
     */
    @WebMethod
    @WebResult(name = "associationRequest", targetNamespace = "")
    @RequestWrapper(localName = "createAssociationRequest", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CreateAssociationRequest")
    @ResponseWrapper(localName = "createAssociationRequestResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CreateAssociationRequestResponse")
    public AssociationRequestBean createAssociationRequest(
        @WebParam(name = "newAssociationRequest", targetNamespace = "")
        NewAssociationRequestBean newAssociationRequest);

    /**
     * 
     * @param authToken
     * @param sessionId
     * @return
     *     returns ar.edu.ubp.soap.ws.SessionBean
     */
    @WebMethod
    @WebResult(name = "session", targetNamespace = "")
    @RequestWrapper(localName = "markSessionAsUsed", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.MarkSessionAsUsed")
    @ResponseWrapper(localName = "markSessionAsUsedResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.MarkSessionAsUsedResponse")
    public SessionBean markSessionAsUsed(
        @WebParam(name = "sessionId", targetNamespace = "")
        Integer sessionId,
        @WebParam(name = "authToken", targetNamespace = "")
        String authToken);

    /**
     * 
     * @param authToken
     * @param userToken
     * @return
     *     returns ar.edu.ubp.soap.ws.AssociationRequestBean
     */
    @WebMethod
    @WebResult(name = "associationRequest", targetNamespace = "")
    @RequestWrapper(localName = "cancelAssociationRequest", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CancelAssociationRequest")
    @ResponseWrapper(localName = "cancelAssociationRequestResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CancelAssociationRequestResponse")
    public AssociationRequestBean cancelAssociationRequest(
        @WebParam(name = "authToken", targetNamespace = "")
        String authToken,
        @WebParam(name = "userToken", targetNamespace = "")
        String userToken);

    /**
     * 
     * @param newPlatformUser
     * @param uuid
     * @return
     *     returns ar.edu.ubp.soap.ws.AssociationRequestBean
     */
    @WebMethod
    @WebResult(name = "signupAssociationCompleted", targetNamespace = "")
    @RequestWrapper(localName = "completeSignupAssociationRequest", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CompleteSignupAssociationRequest")
    @ResponseWrapper(localName = "completeSignupAssociationRequestResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CompleteSignupAssociationRequestResponse")
    public AssociationRequestBean completeSignupAssociationRequest(
        @WebParam(name = "newPlatformUser", targetNamespace = "")
        NewPlatformUserBean newPlatformUser,
        @WebParam(name = "uuid", targetNamespace = "")
        String uuid);

    /**
     * 
     * @param newSession
     * @return
     *     returns ar.edu.ubp.soap.ws.SessionBean
     */
    @WebMethod
    @WebResult(name = "session", targetNamespace = "")
    @RequestWrapper(localName = "createSession", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CreateSession")
    @ResponseWrapper(localName = "createSessionResponse", targetNamespace = "http://ws.soap.ubp.edu.ar/", className = "ar.edu.ubp.soap.ws.CreateSessionResponse")
    public SessionBean createSession(
        @WebParam(name = "newSession", targetNamespace = "")
        NewSessionBean newSession);

}
