package ar.edu.ubp.rest.portal.beans.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserPayloadBean extends AbstractServicePayload {
    private String userToken;

    @Builder
    public UserPayloadBean(
            String authToken,
            String userToken) {
        this.authToken = authToken;
        this.userToken = userToken;
    }

    @Override
    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();

        xmlBuilder.append("<authToken>").append(this.authToken).append("</authToken>\n");
        xmlBuilder.append("<userToken>").append(this.userToken).append("</userToken>\n");

        return xmlBuilder.toString();
    }

}
