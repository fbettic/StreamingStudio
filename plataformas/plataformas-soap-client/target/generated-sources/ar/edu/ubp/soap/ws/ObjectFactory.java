
package ar.edu.ubp.soap.ws;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ar.edu.ubp.soap.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _CompleteLoginAssociationRequest_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "completeLoginAssociationRequest");
    private static final QName _CompleteLoginAssociationRequestResponse_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "completeLoginAssociationRequestResponse");
    private static final QName _CompleteSignupAssociationRequest_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "completeSignupAssociationRequest");
    private static final QName _CompleteSignupAssociationRequestResponse_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "completeSignupAssociationRequestResponse");
    private static final QName _CreateAssociationRequest_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "createAssociationRequest");
    private static final QName _CreateAssociationRequestResponse_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "createAssociationRequestResponse");
    private static final QName _CreatePlatformUser_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "createPlatformUser");
    private static final QName _CreatePlatformUserResponse_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "createPlatformUserResponse");
    private static final QName _CreateSession_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "createSession");
    private static final QName _CreateSessionResponse_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "createSessionResponse");
    private static final QName _GetAllFilms_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "getAllFilms");
    private static final QName _GetAllFilmsResponse_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "getAllFilmsResponse");
    private static final QName _GetAssociationData_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "getAssociationData");
    private static final QName _GetAssociationDataResponse_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "getAssociationDataResponse");
    private static final QName _Ping_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "ping");
    private static final QName _PingResponse_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "pingResponse");
    private static final QName _Exception_QNAME = new QName("http://ws.soap.ubp.edu.ar/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ar.edu.ubp.soap.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CompleteLoginAssociationRequest }
     * 
     * @return
     *     the new instance of {@link CompleteLoginAssociationRequest }
     */
    public CompleteLoginAssociationRequest createCompleteLoginAssociationRequest() {
        return new CompleteLoginAssociationRequest();
    }

    /**
     * Create an instance of {@link CompleteLoginAssociationRequestResponse }
     * 
     * @return
     *     the new instance of {@link CompleteLoginAssociationRequestResponse }
     */
    public CompleteLoginAssociationRequestResponse createCompleteLoginAssociationRequestResponse() {
        return new CompleteLoginAssociationRequestResponse();
    }

    /**
     * Create an instance of {@link CompleteSignupAssociationRequest }
     * 
     * @return
     *     the new instance of {@link CompleteSignupAssociationRequest }
     */
    public CompleteSignupAssociationRequest createCompleteSignupAssociationRequest() {
        return new CompleteSignupAssociationRequest();
    }

    /**
     * Create an instance of {@link CompleteSignupAssociationRequestResponse }
     * 
     * @return
     *     the new instance of {@link CompleteSignupAssociationRequestResponse }
     */
    public CompleteSignupAssociationRequestResponse createCompleteSignupAssociationRequestResponse() {
        return new CompleteSignupAssociationRequestResponse();
    }

    /**
     * Create an instance of {@link CreateAssociationRequest }
     * 
     * @return
     *     the new instance of {@link CreateAssociationRequest }
     */
    public CreateAssociationRequest createCreateAssociationRequest() {
        return new CreateAssociationRequest();
    }

    /**
     * Create an instance of {@link CreateAssociationRequestResponse }
     * 
     * @return
     *     the new instance of {@link CreateAssociationRequestResponse }
     */
    public CreateAssociationRequestResponse createCreateAssociationRequestResponse() {
        return new CreateAssociationRequestResponse();
    }

    /**
     * Create an instance of {@link CreatePlatformUser }
     * 
     * @return
     *     the new instance of {@link CreatePlatformUser }
     */
    public CreatePlatformUser createCreatePlatformUser() {
        return new CreatePlatformUser();
    }

    /**
     * Create an instance of {@link CreatePlatformUserResponse }
     * 
     * @return
     *     the new instance of {@link CreatePlatformUserResponse }
     */
    public CreatePlatformUserResponse createCreatePlatformUserResponse() {
        return new CreatePlatformUserResponse();
    }

    /**
     * Create an instance of {@link CreateSession }
     * 
     * @return
     *     the new instance of {@link CreateSession }
     */
    public CreateSession createCreateSession() {
        return new CreateSession();
    }

    /**
     * Create an instance of {@link CreateSessionResponse }
     * 
     * @return
     *     the new instance of {@link CreateSessionResponse }
     */
    public CreateSessionResponse createCreateSessionResponse() {
        return new CreateSessionResponse();
    }

    /**
     * Create an instance of {@link GetAllFilms }
     * 
     * @return
     *     the new instance of {@link GetAllFilms }
     */
    public GetAllFilms createGetAllFilms() {
        return new GetAllFilms();
    }

    /**
     * Create an instance of {@link GetAllFilmsResponse }
     * 
     * @return
     *     the new instance of {@link GetAllFilmsResponse }
     */
    public GetAllFilmsResponse createGetAllFilmsResponse() {
        return new GetAllFilmsResponse();
    }

    /**
     * Create an instance of {@link GetAssociationData }
     * 
     * @return
     *     the new instance of {@link GetAssociationData }
     */
    public GetAssociationData createGetAssociationData() {
        return new GetAssociationData();
    }

    /**
     * Create an instance of {@link GetAssociationDataResponse }
     * 
     * @return
     *     the new instance of {@link GetAssociationDataResponse }
     */
    public GetAssociationDataResponse createGetAssociationDataResponse() {
        return new GetAssociationDataResponse();
    }

    /**
     * Create an instance of {@link Ping }
     * 
     * @return
     *     the new instance of {@link Ping }
     */
    public Ping createPing() {
        return new Ping();
    }

    /**
     * Create an instance of {@link PingResponse }
     * 
     * @return
     *     the new instance of {@link PingResponse }
     */
    public PingResponse createPingResponse() {
        return new PingResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     * @return
     *     the new instance of {@link Exception }
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link AssociationRequestBean }
     * 
     * @return
     *     the new instance of {@link AssociationRequestBean }
     */
    public AssociationRequestBean createAssociationRequestBean() {
        return new AssociationRequestBean();
    }

    /**
     * Create an instance of {@link LoginRequestBean }
     * 
     * @return
     *     the new instance of {@link LoginRequestBean }
     */
    public LoginRequestBean createLoginRequestBean() {
        return new LoginRequestBean();
    }

    /**
     * Create an instance of {@link FilmBean }
     * 
     * @return
     *     the new instance of {@link FilmBean }
     */
    public FilmBean createFilmBean() {
        return new FilmBean();
    }

    /**
     * Create an instance of {@link NewAssociationRequestBean }
     * 
     * @return
     *     the new instance of {@link NewAssociationRequestBean }
     */
    public NewAssociationRequestBean createNewAssociationRequestBean() {
        return new NewAssociationRequestBean();
    }

    /**
     * Create an instance of {@link NewPlatformUserBean }
     * 
     * @return
     *     the new instance of {@link NewPlatformUserBean }
     */
    public NewPlatformUserBean createNewPlatformUserBean() {
        return new NewPlatformUserBean();
    }

    /**
     * Create an instance of {@link PlatformUserBean }
     * 
     * @return
     *     the new instance of {@link PlatformUserBean }
     */
    public PlatformUserBean createPlatformUserBean() {
        return new PlatformUserBean();
    }

    /**
     * Create an instance of {@link NewSessionBean }
     * 
     * @return
     *     the new instance of {@link NewSessionBean }
     */
    public NewSessionBean createNewSessionBean() {
        return new NewSessionBean();
    }

    /**
     * Create an instance of {@link SessionBean }
     * 
     * @return
     *     the new instance of {@link SessionBean }
     */
    public SessionBean createSessionBean() {
        return new SessionBean();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompleteLoginAssociationRequest }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CompleteLoginAssociationRequest }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "completeLoginAssociationRequest")
    public JAXBElement<CompleteLoginAssociationRequest> createCompleteLoginAssociationRequest(CompleteLoginAssociationRequest value) {
        return new JAXBElement<>(_CompleteLoginAssociationRequest_QNAME, CompleteLoginAssociationRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompleteLoginAssociationRequestResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CompleteLoginAssociationRequestResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "completeLoginAssociationRequestResponse")
    public JAXBElement<CompleteLoginAssociationRequestResponse> createCompleteLoginAssociationRequestResponse(CompleteLoginAssociationRequestResponse value) {
        return new JAXBElement<>(_CompleteLoginAssociationRequestResponse_QNAME, CompleteLoginAssociationRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompleteSignupAssociationRequest }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CompleteSignupAssociationRequest }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "completeSignupAssociationRequest")
    public JAXBElement<CompleteSignupAssociationRequest> createCompleteSignupAssociationRequest(CompleteSignupAssociationRequest value) {
        return new JAXBElement<>(_CompleteSignupAssociationRequest_QNAME, CompleteSignupAssociationRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompleteSignupAssociationRequestResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CompleteSignupAssociationRequestResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "completeSignupAssociationRequestResponse")
    public JAXBElement<CompleteSignupAssociationRequestResponse> createCompleteSignupAssociationRequestResponse(CompleteSignupAssociationRequestResponse value) {
        return new JAXBElement<>(_CompleteSignupAssociationRequestResponse_QNAME, CompleteSignupAssociationRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAssociationRequest }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateAssociationRequest }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "createAssociationRequest")
    public JAXBElement<CreateAssociationRequest> createCreateAssociationRequest(CreateAssociationRequest value) {
        return new JAXBElement<>(_CreateAssociationRequest_QNAME, CreateAssociationRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAssociationRequestResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateAssociationRequestResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "createAssociationRequestResponse")
    public JAXBElement<CreateAssociationRequestResponse> createCreateAssociationRequestResponse(CreateAssociationRequestResponse value) {
        return new JAXBElement<>(_CreateAssociationRequestResponse_QNAME, CreateAssociationRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePlatformUser }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreatePlatformUser }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "createPlatformUser")
    public JAXBElement<CreatePlatformUser> createCreatePlatformUser(CreatePlatformUser value) {
        return new JAXBElement<>(_CreatePlatformUser_QNAME, CreatePlatformUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePlatformUserResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreatePlatformUserResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "createPlatformUserResponse")
    public JAXBElement<CreatePlatformUserResponse> createCreatePlatformUserResponse(CreatePlatformUserResponse value) {
        return new JAXBElement<>(_CreatePlatformUserResponse_QNAME, CreatePlatformUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSession }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateSession }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "createSession")
    public JAXBElement<CreateSession> createCreateSession(CreateSession value) {
        return new JAXBElement<>(_CreateSession_QNAME, CreateSession.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSessionResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateSessionResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "createSessionResponse")
    public JAXBElement<CreateSessionResponse> createCreateSessionResponse(CreateSessionResponse value) {
        return new JAXBElement<>(_CreateSessionResponse_QNAME, CreateSessionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllFilms }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAllFilms }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "getAllFilms")
    public JAXBElement<GetAllFilms> createGetAllFilms(GetAllFilms value) {
        return new JAXBElement<>(_GetAllFilms_QNAME, GetAllFilms.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllFilmsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAllFilmsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "getAllFilmsResponse")
    public JAXBElement<GetAllFilmsResponse> createGetAllFilmsResponse(GetAllFilmsResponse value) {
        return new JAXBElement<>(_GetAllFilmsResponse_QNAME, GetAllFilmsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAssociationData }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAssociationData }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "getAssociationData")
    public JAXBElement<GetAssociationData> createGetAssociationData(GetAssociationData value) {
        return new JAXBElement<>(_GetAssociationData_QNAME, GetAssociationData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAssociationDataResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAssociationDataResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "getAssociationDataResponse")
    public JAXBElement<GetAssociationDataResponse> createGetAssociationDataResponse(GetAssociationDataResponse value) {
        return new JAXBElement<>(_GetAssociationDataResponse_QNAME, GetAssociationDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ping }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Ping }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "ping")
    public JAXBElement<Ping> createPing(Ping value) {
        return new JAXBElement<>(_Ping_QNAME, Ping.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PingResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PingResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "pingResponse")
    public JAXBElement<PingResponse> createPingResponse(PingResponse value) {
        return new JAXBElement<>(_PingResponse_QNAME, PingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.soap.ubp.edu.ar/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<>(_Exception_QNAME, Exception.class, null, value);
    }

}
