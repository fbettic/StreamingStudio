package ar.edu.ubp.rest.portal.beans.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SessionPayloadBean extends AbstractServicePayload {
    private String filmCode;
    private String userToken;

    public SessionPayloadBean(
            String authToken,
            String filmCode,
            String userToken) {
        this.authToken = authToken;
        this.filmCode = filmCode;
        this.userToken = userToken;
    }

    @Override
    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();

        xmlBuilder.append("<newSession>\n");
        xmlBuilder.append("<filmCode>").append(this.filmCode).append("</filmCode>\n");
        xmlBuilder.append("<authToken>").append(this.authToken).append("</authToken>\n");
        xmlBuilder.append("<userToken>").append(this.userToken).append("</userToken>\n");
        xmlBuilder.append("</newSession>");

        return xmlBuilder.toString();
    }

}
