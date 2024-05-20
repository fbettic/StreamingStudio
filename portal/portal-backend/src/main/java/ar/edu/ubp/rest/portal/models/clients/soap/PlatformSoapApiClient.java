package ar.edu.ubp.rest.portal.models.clients.soap;

import java.util.List;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.w3c.dom.Document;

import ar.edu.ubp.rest.portal.beans.request.AbstractServicePayload;
import ar.edu.ubp.rest.portal.beans.request.AssociationPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.AssociationRequestPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.ServicePayloadBean;
import ar.edu.ubp.rest.portal.beans.request.SessionPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.UserPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyPlatformReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AssociationResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BasicResponseBean;
import ar.edu.ubp.rest.portal.beans.response.FilmResponseBean;
import ar.edu.ubp.rest.portal.beans.response.SessionResponseBean;
import ar.edu.ubp.rest.portal.models.clients.AbstractPlatformApiClient;

public class PlatformSoapApiClient extends AbstractPlatformApiClient {
    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
    private static final String NAMESPACE_URI = "http://ws.soap.ubp.edu.ar/";
    private final SoapRequestUtils soapRequestUtils;

    public PlatformSoapApiClient() {
        this.soapRequestUtils = new SoapRequestUtils(webServiceTemplate);
    }

    private Document sendSoapRequest(String action, AbstractServicePayload payload) {
        return soapRequestUtils.sendSoapRequest(url, action, NAMESPACE_URI, payload);
    }

    @Override
    public BasicResponseBean ping(ServicePayloadBean payload) {
        Document resultDocument = sendSoapRequest("ping", payload);

        return soapRequestUtils.extractSingleElement(resultDocument, "pong", BasicResponseBean::new);
    }

    @Override
    public List<FilmResponseBean> getAllFilms(ServicePayloadBean payload) {
        Document resultDocument = sendSoapRequest("getAllFilms", payload);
        return soapRequestUtils.extractElements(resultDocument, "films", FilmResponseBean::new);
    }

    @Override
    public AssociationResponseBean createAssociationRequest(AssociationRequestPayloadBean payload) {

        Document resultDocument = sendSoapRequest("createAssociationRequest", payload);

        return soapRequestUtils.extractSingleElement(resultDocument, "associationRequest",
                AssociationResponseBean::new);
    }

    @Override
    public AssociationResponseBean getAssociationData(AssociationPayloadBean payload) {

        Document resultDocument = sendSoapRequest("getAssociationData", payload);

        return soapRequestUtils.extractSingleElement(resultDocument, "associationRequest",
                AssociationResponseBean::new);
    }

    @Override
    public AssociationResponseBean cancelAssociationRequest(UserPayloadBean payload) {

        Document resultDocument = sendSoapRequest("cancelAssociationRequest", payload);

        return soapRequestUtils.extractSingleElement(resultDocument, "associationRequest",
                AssociationResponseBean::new);
    }

    @Override
    public SessionResponseBean createSession(SessionPayloadBean payload) {
        Document resultDocument = sendSoapRequest("createSession", payload);

        return soapRequestUtils.extractSingleElement(resultDocument, "session",
                SessionResponseBean::new);
    }

    @Override
    public BasicResponseBean sendWeeklyReport(WeeklyPlatformReportPayloadBean payload) {

        Document resultDocument = sendSoapRequest("receiveWeeklyReport", payload);

        return soapRequestUtils.extractSingleElement(resultDocument, "report", BasicResponseBean::new);
    }

}
