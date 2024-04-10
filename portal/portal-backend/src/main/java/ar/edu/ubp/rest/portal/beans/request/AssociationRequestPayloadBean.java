package ar.edu.ubp.rest.portal.beans.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AssociationRequestPayloadBean extends ServiceRequestPayloadBean {
    private String associationType;
    private String redirectUrl;

    @Builder
    public AssociationRequestPayloadBean(
            String authToken,
            String associationType,
            String redirectUrl) {
        this.authToken = authToken;
        this.associationType = associationType;
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();

        xmlBuilder.append("<newAssociationRequest>\n");
        xmlBuilder.append("<associationType>").append(this.associationType).append("</associationType>\n");
        xmlBuilder.append("<authToken>").append(this.authToken).append("</authToken>\n");
        xmlBuilder.append("<redirectUrl>").append(this.redirectUrl).append("</redirectUrl>\n");
        xmlBuilder.append("</newAssociationRequest>");

        return xmlBuilder.toString();
    }
}
