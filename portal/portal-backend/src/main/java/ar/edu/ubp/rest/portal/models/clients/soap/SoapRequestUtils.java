package ar.edu.ubp.rest.portal.models.clients.soap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.springframework.ws.WebServiceException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ar.edu.ubp.rest.portal.beans.request.AbstractServicePayload;
import ar.edu.ubp.rest.portal.beans.response.ServiceResponseBean;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SoapRequestUtils {
    private final WebServiceTemplate webServiceTemplate;

    public Document sendSoapRequest(String url, String action, String namespace, AbstractServicePayload payload) {
        String message = """
                <ws:%s xmlns:ws="%s">
                   %s
                </ws:%s>""".formatted(action, namespace, payload.toSoapXml(), action);

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

        try {
            webServiceTemplate.sendSourceAndReceiveToResult(url, source, result);
        } catch (SoapFaultClientException e) {
            throw new RuntimeException("SOAP Fault occurred: " + e.getFaultStringOrReason(), e);
        } catch (WebServiceException e) {
            throw new RuntimeException("WebService exception occurred: " + e.getMessage(), e);
        }

        return resultDocument;
    }

    public <T extends ServiceResponseBean> T extractSingleElement(Document document, String tagName,
            Function<Element, T> constructor) {
        NodeList nodeList = document.getElementsByTagName(tagName);

        if (nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);
            return constructor.apply(element);
        } else {
            throw new RuntimeException("Response does not contain <" + tagName + "> element");
        }
    }

    public <T extends ServiceResponseBean> List<T> extractElements(Document document, String tagName,
            Function<Element, T> constructor) {
        List<T> elements = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName(tagName);

        if (nodeList.getLength() <= 0) {
            throw new RuntimeException("Response does not contain <" + tagName + "> element");
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            elements.add(constructor.apply(element));
        }

        return elements;
    }

}
