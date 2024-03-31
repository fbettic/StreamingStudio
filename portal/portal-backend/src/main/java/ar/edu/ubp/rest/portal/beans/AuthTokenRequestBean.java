package ar.edu.ubp.rest.portal.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenRequestBean {

    private String authToken;

    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();

        xmlBuilder.append("<authToken>").append(this.authToken).append("</authToken>\n");

        return xmlBuilder.toString();
    }

}
