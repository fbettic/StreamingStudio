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
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SoapRequestUtil {
    private final WebServiceTemplate webServiceTemplate;

    public Document createDocumentFromMessage(String message) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(message.getBytes()));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("Error creating document from message: " + e.getMessage(), e);
        }
    }

    public Document sendSoapRequest(String url, String action, String namespace, AbstractServicePayload payload) {
        String message = String.format("""
                <ws:%s xmlns:ws="%s">
                   %s
                </ws:%s>""", action, namespace, payload.toSoapXml(), action);

        Document sourceDocument = createDocumentFromMessage(message);
        Document resultDocument = createEmptyDocument();

        Source source = new DOMSource(sourceDocument);
        Result result = new DOMResult(resultDocument);

        webServiceTemplate.sendSourceAndReceiveToResult(url, source, result);

        return resultDocument;
    }

    public Document createEmptyDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.newDocument();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Error creating empty document: " + e.getMessage(), e);
        }
    }

    public String extractSingleElementValue(Document document, String tagName) {
        NodeList nodes = document.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        } else {
            throw new RuntimeException("Response does not contain <" + tagName + "> element");
        }
    }

    public <T> List<T> extractElements(Document document, String tagName, ElementExtractor<T> extractor) {
        NodeList nodes = document.getElementsByTagName(tagName);
        List<T> elements = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            elements.add(extractor.extract((Element) nodes.item(i)));
        }
        return elements;
    }

    @FunctionalInterface
    private interface ElementExtractor<T> {
        T extract(Element element);
    }

}
