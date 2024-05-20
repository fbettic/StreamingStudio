package ar.edu.ubp.rest.portal.models.clients.soap;

import java.util.List;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.w3c.dom.Document;

import ar.edu.ubp.rest.portal.beans.request.AbstractServicePayload;
import ar.edu.ubp.rest.portal.beans.request.AdvertisingPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.ServicePayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyAdvertiserReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BasicResponseBean;
import ar.edu.ubp.rest.portal.models.clients.AbstractAdvertiserApiClient;

public class AdvertiserSoapApiClient extends AbstractAdvertiserApiClient {

    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
    private static final String NAMESPACE_URI = "http://ws.soap.ubp.edu.ar/";
    private final SoapRequestUtils soapRequestUtils;

    public AdvertiserSoapApiClient() {
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
    public AdvertisingResponseBean getAdvertisingById(AdvertisingPayloadBean payload) {
        Document resultDocument = sendSoapRequest("getAdvertisingById", payload);
        return soapRequestUtils.extractSingleElement(resultDocument, "advertising", AdvertisingResponseBean::new);
    }

    @Override
    public List<AdvertisingResponseBean> getAllAdvertisings(ServicePayloadBean payload) {
        Document resultDocument = sendSoapRequest("getAllAdvertisings", payload);
        return soapRequestUtils.extractElements(resultDocument, "advertisings", AdvertisingResponseBean::new);
    }

    @Override
    public BasicResponseBean sendWeeklyReport(WeeklyAdvertiserReportPayloadBean payload) {
        Document resultDocument = sendSoapRequest("receiveWeeklyReport", payload);
        return soapRequestUtils.extractSingleElement(resultDocument, "report", BasicResponseBean::new);
    }

}
