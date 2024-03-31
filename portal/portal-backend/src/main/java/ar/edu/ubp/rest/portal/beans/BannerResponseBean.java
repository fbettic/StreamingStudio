package ar.edu.ubp.rest.portal.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
public class BannerResponseBean {
    private Integer bannerId;
    private String imageUrl;
    private String redirectUrl;
    private String text;

    public BannerResponseBean(Document xmlDocument) {
        Element bannerElement = (Element) xmlDocument.getElementsByTagName("banner").item(0);
        this.bannerId = Integer.parseInt(getElementValue(bannerElement, "bannerId"));
        this.imageUrl = getElementValue(bannerElement, "imageUrl");
        this.redirectUrl = getElementValue(bannerElement, "redirectUrl");
        this.text = getElementValue(bannerElement, "text");
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
