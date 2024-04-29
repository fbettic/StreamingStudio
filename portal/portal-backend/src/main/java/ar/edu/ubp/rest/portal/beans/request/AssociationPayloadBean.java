package ar.edu.ubp.rest.portal.beans.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AssociationPayloadBean extends ServiceRequestPayloadBean {
    private Integer associationId;

    @Builder
    public AssociationPayloadBean(String authToken, Integer associationId) {
        this.authToken = authToken;
        this.associationId = associationId;
    }

    @Override
    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();

        xmlBuilder.append("<authToken>").append(this.authToken).append("</authToken>\n");
        xmlBuilder.append("<associationId>").append(this.associationId).append("</associationId>\n");

        return xmlBuilder.toString();
    }
}
