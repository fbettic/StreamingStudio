package ar.edu.ubp.rest.portal.beans.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServicePayloadBean extends AbstractServicePayload {
    
    @Builder
    public ServicePayloadBean(
            String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<authToken>").append(this.authToken).append("</authToken>\n");
        return xmlBuilder.toString();
    }

}
