package ar.edu.ubp.rest.portal.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisingResponseBean {
    private Integer advertisingId;
    private Integer bannerId;
    private Integer serviceId;
    private String imageUrl;
    private String priorityTitle;
    private String redirectUrl;
    private String text;
    private Date fromDate;
    private Date toDate;

    public AdvertisingResponseBean(Document xmlDocument) {
        Element advertisingElement = (Element) xmlDocument.getElementsByTagName("advertisings").item(0);
        this.advertisingId = Integer.parseInt(getElementValue(advertisingElement, "advertisingId"));
        this.bannerId = Integer.parseInt(getElementValue(advertisingElement, "bannerId"));
        this.serviceId = Integer.parseInt(getElementValue(advertisingElement, "serviceId"));
        this.imageUrl = getElementValue(advertisingElement, "imageUrl");
        this.priorityTitle = getElementValue(advertisingElement, "priorityTitle");
        this.redirectUrl = getElementValue(advertisingElement, "redirectUrl");
        this.text = getElementValue(advertisingElement, "text");
        try {
            this.fromDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(getElementValue(advertisingElement, "fromDate"));
            this.toDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(getElementValue(advertisingElement, "toDate"));
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing dates", e);
        }
    }

    private String getElementValue(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            return null;
        }
    }
}
