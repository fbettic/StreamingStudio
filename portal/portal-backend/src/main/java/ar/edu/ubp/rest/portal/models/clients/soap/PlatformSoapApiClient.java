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

import ar.edu.ubp.rest.portal.beans.request.AssociationPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.AssociationRequestPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.BasicPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.SessionPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AssociationResponseBean;
import ar.edu.ubp.rest.portal.beans.response.FilmResponseBean;
import ar.edu.ubp.rest.portal.beans.response.SessionResponseBean;
import ar.edu.ubp.rest.portal.models.clients.AbstractPlatformApiClient;

public class PlatformSoapApiClient extends AbstractPlatformApiClient {
    private final WebServiceTemplate webServiceTemplate;

    public PlatformSoapApiClient() {
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
    public String ping(BasicPayloadBean payload) {
        String message = """
                <ws:ping xmlns:ws="http://ws.soap.ubp.edu.ar/">
                   %s
                </ws:ping>""".formatted(payload.toSoapXml());

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
    public List<FilmResponseBean> getAllFilms(BasicPayloadBean payload) {
        String message = """
                <ws:getAllFilms xmlns:ws="http://ws.soap.ubp.edu.ar/">
                   %s
                </ws:getAllFilms>""".formatted(payload.toSoapXml());

        Document resultDocument = createAndSendSoapRequest(message);

        Element root = resultDocument.getDocumentElement();
        NodeList filmNodes = root.getElementsByTagName("films");
        List<FilmResponseBean> filmList = new ArrayList<>();

        for (int i = 0; i < filmNodes.getLength(); i++) {
            Element filmElement = (Element) filmNodes.item(i);
            filmList.add(new FilmResponseBean(filmElement.getOwnerDocument()));
        }

        return filmList;
    }

    @Override
    public AssociationResponseBean createAssociationRequest(AssociationRequestPayloadBean payload) {
        String message = """
                <ws:createAssociationRequest xmlns:ws="http://ws.soap.ubp.edu.ar/">
                   %s
                </ws:createAssociationRequest>""".formatted(payload.toSoapXml());

        Document resultDocument = createAndSendSoapRequest(message);

        Element root = resultDocument.getDocumentElement();
        NodeList associationNodes = root.getElementsByTagName("associationRequest");

        if (associationNodes.getLength() == 0) {
            return null;
        }

        Element associationElement = (Element) associationNodes.item(0);
        return new AssociationResponseBean(associationElement.getOwnerDocument());
    }

    @Override
    public AssociationResponseBean getAssociationData(AssociationPayloadBean payload) {
        String message = """
                <ws:getUserToken xmlns:ws="http://ws.soap.ubp.edu.ar/">
                   %s
                </ws:getUserToken>""".formatted(payload.toSoapXml());

        Document resultDocument = createAndSendSoapRequest(message);

        Element root = resultDocument.getDocumentElement();
        NodeList associationNodes = root.getElementsByTagName("associationRequest");

        if (associationNodes.getLength() == 0) {
            return null;
        }

        Element associationElement = (Element) associationNodes.item(0);
        return new AssociationResponseBean(associationElement.getOwnerDocument());
    }

    @Override
    public SessionResponseBean createSession(SessionPayloadBean payload) {
        String message = """
                <ws:createSession xmlns:ws="http://ws.soap.ubp.edu.ar/">
                   %s
                </ws:createSession>""".formatted(payload.toSoapXml());

        Document resultDocument = createAndSendSoapRequest(message);

        Element root = resultDocument.getDocumentElement();
        NodeList sessionNodes = root.getElementsByTagName("sessionRequest");

        if (sessionNodes.getLength() == 0) {
            return null;
        }

        Element sessionElement = (Element) sessionNodes.item(0);
        return new SessionResponseBean(sessionElement.getOwnerDocument());
    }

}
