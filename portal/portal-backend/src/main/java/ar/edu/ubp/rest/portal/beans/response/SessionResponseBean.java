package ar.edu.ubp.rest.portal.beans.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class SessionResponseBean extends ServiceResponseBean {
    private Integer associationId;
    private Integer sessionId;
    private String filmCode;
    private String sessionUrl;
    private Boolean expired;
    private Date createdAt;

    public SessionResponseBean(Document xmlDocument){
        Element sessionElement = (Element) xmlDocument.getElementsByTagName("associationRequest").item(0);
        this.associationId = Integer.parseInt(getElementValue(sessionElement, "associationId"));
        this.sessionId = Integer.parseInt(getElementValue(sessionElement, "sessionId"));
        this.filmCode = getElementValue(sessionElement, "filmCode");
        this.sessionUrl = getElementValue(sessionElement, "sessionUrl");
        this.expired = Boolean.parseBoolean(getElementValue(sessionElement, "expired"));
        try {
            this.createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(getElementValue(sessionElement, "requestedAt"));
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing dates", e);
        }
    }
}
