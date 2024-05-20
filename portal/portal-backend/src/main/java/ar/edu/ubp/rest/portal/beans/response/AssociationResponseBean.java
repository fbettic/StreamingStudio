package ar.edu.ubp.rest.portal.beans.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
@EqualsAndHashCode(callSuper = false)
public class AssociationResponseBean extends ServiceResponseBean {
    private Integer associationId;
    private Integer userId;
    private Integer serviceId;
    private String associationType;
    private String state;
    private String authUrl;
    private String redirectUrl;
    private Date requestedAt;
    private String userToken;

    public AssociationResponseBean(Element associationElement) {

        this.associationId = Integer.parseInt(getElementValue(associationElement, "associationId"));
        this.serviceId = Integer.parseInt(getElementValue(associationElement, "serviceId"));
        this.associationType = getElementValue(associationElement, "associationType");
        this.state = getElementValue(associationElement, "state");
        this.authUrl = getElementValue(associationElement, "authUrl");
        this.redirectUrl = getElementValue(associationElement, "redirectUrl");
        this.userToken = getElementValue(associationElement, "userToken");

        try {
            this.requestedAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
                    .parse(getElementValue(associationElement, "requestedAt"));
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing dates", e);
        }
    }

}
