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

import ar.edu.ubp.rest.portal.beans.request.BasicPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BannerResponseBean;
import ar.edu.ubp.rest.portal.models.clients.AbstractAdvertiserApiClient;

public class AdvertiserSoapApiClient extends AbstractAdvertiserApiClient {
    private final WebServiceTemplate webServiceTemplate;

    public AdvertiserSoapApiClient() {
        this.webServiceTemplate = new WebServiceTemplate();
    }

    private Document createAndSendSoapRequest(String soapMessage) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document resultDocument;
        Document sourceDocument;
        try {
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            resultDocument = docBuilder.newDocument();
            sourceDocument = docBuilder.parse(new ByteArrayInputStream(soapMessage.getBytes()));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        Source source = new DOMSource(sourceDocument);
        Result result = new DOMResult(resultDocument);

        webServiceTemplate.sendSourceAndReceiveToResult(this.url, source, result);

        return resultDocument;
    }

    @Override
    public String ping(BasicPayloadBean authToken) {
        String message = """
                <ws:ping xmlns:ws="http://ws.soap.ubp.edu.ar/">
                   %s
                </ws:ping>""".formatted(authToken.toSoapXml());

        Document resultDocument = createAndSendSoapRequest(message);

        Element root = resultDocument.getDocumentElement();
        NodeList pongNodeList = root.getElementsByTagName("pong");
        
        
        if (pongNodeList.getLength() > 0) {
            return pongNodeList.item(0).getTextContent();
        } else {
            throw new RuntimeException("Response does not contain <pong> element");
        }
    }

    @Override
    public BannerResponseBean getBannerById(Integer id, BasicPayloadBean authToken) {
        String message = """
                <ws:getBannerById xmlns:ws="http://ws.soap.ubp.edu.ar/">
                   <bannerId>%d</bannerId>
                   %s
                </ws:getBannerById>""".formatted(id, authToken.toSoapXml());

        Document resultDocument = createAndSendSoapRequest(message);

        Element root = resultDocument.getDocumentElement();
        NodeList bannerNodes = root.getElementsByTagName("banner");

        if (bannerNodes.getLength() == 0) {
            return null;
        }
        
        Element bannerElement = (Element) bannerNodes.item(0);
        return new BannerResponseBean(bannerElement.getOwnerDocument());
    }

    @Override
    public List<AdvertisingResponseBean> getAllAdvertisings(BasicPayloadBean authToken) {
        String message = """
                <ws:getAllAdvertisings xmlns:ws="http://ws.soap.ubp.edu.ar/">
                   %s
                </ws:getAllAdvertisings>""".formatted(authToken.toSoapXml());

        Document resultDocument = createAndSendSoapRequest(message);

        Element root = resultDocument.getDocumentElement();
        NodeList advertisingNodes = root.getElementsByTagName("advertisings");
        List<AdvertisingResponseBean> advertisingList = new ArrayList<>();

        for (int i = 0; i < advertisingNodes.getLength(); i++) {
            Element advertisingElement = (Element) advertisingNodes.item(i);
            advertisingList.add(new AdvertisingResponseBean(advertisingElement.getOwnerDocument()));
        }

        return advertisingList;
    }

}
