package ar.edu.ubp.rest.portal.models.clients.soap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ar.edu.ubp.rest.portal.beans.request.AbstractServicePayload;
import ar.edu.ubp.rest.portal.beans.request.BannerPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.ServicePayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyAdvertiserReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BannerResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BasicResponseBean;
import ar.edu.ubp.rest.portal.models.clients.AbstractAdvertiserApiClient;

public class AdvertiserSoapApiClient extends AbstractAdvertiserApiClient {
    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
    private static final String NAMESPACE_URI = "http://ws.soap.ubp.edu.ar/";


    private Document createAndSendSoapRequest(String action, AbstractServicePayload payload) {
        String message = """
                <ws:%s xmlns:ws="%s">
                   %s
                </ws:%s>""".formatted(action, NAMESPACE_URI, payload.toSoapXml(), action);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document resultDocument;
        Document sourceDocument;

        try {
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            resultDocument = docBuilder.newDocument();
            sourceDocument = docBuilder.parse(new ByteArrayInputStream(message.getBytes()));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException("Error while creating and sending SOAP request: " + e.getMessage(), e);
        }

        Source source = new DOMSource(sourceDocument);
        Result result = new DOMResult(resultDocument);

        webServiceTemplate.sendSourceAndReceiveToResult(this.url, source, result);

        return resultDocument;
    }

    @Override
    public String ping(ServicePayloadBean authToken) {

        Document resultDocument = createAndSendSoapRequest("ping",authToken);

        Element root = resultDocument.getDocumentElement();
        NodeList nodes = root.getElementsByTagName("pong");

        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        } else {
            throw new RuntimeException("Response does not contain <pong> element");
        }
    }

    @Override
    public BannerResponseBean getBannerById(BannerPayloadBean payload) {
        Document resultDocument = createAndSendSoapRequest("getBannerById", payload);

        Element root = resultDocument.getDocumentElement();
        NodeList nodes = root.getElementsByTagName("banner");

        if (nodes.getLength() == 0) {
            return null;
        }

        Element element = (Element) nodes.item(0);
        return new BannerResponseBean(element);
    }

    @Override
    public List<AdvertisingResponseBean> getAllAdvertisings(ServicePayloadBean authToken) {

        Document resultDocument = createAndSendSoapRequest("getAllAdvertisings",authToken);

        Element root = resultDocument.getDocumentElement();
        NodeList nodes = root.getElementsByTagName("advertisings");
        List<AdvertisingResponseBean> advertisingList = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            advertisingList.add(new AdvertisingResponseBean(element));
        }

        return advertisingList;
    }

    @Override
    public BasicResponseBean sendWeeklyReport(WeeklyAdvertiserReportPayloadBean payload) {
        Document resultDocument = createAndSendSoapRequest("receiveWeeklyReport", payload);

        Element root = resultDocument.getDocumentElement();
        NodeList nodes = root.getElementsByTagName("report");

        if (nodes.getLength() == 0) {
            return null;
        }

        Element element = (Element) nodes.item(0);
        return new BasicResponseBean(element);
    }

}
