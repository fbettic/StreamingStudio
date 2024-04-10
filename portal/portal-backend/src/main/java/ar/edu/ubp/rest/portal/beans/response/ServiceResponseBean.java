package ar.edu.ubp.rest.portal.beans.response;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class ServiceResponseBean {

    protected String getElementValue(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            return null;
        }
    }
}
