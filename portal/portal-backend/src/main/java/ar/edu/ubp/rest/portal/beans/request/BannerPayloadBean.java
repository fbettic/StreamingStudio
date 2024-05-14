package ar.edu.ubp.rest.portal.beans.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BannerPayloadBean extends AbstractServicePayload {
    private Integer bannerId;

    public BannerPayloadBean(String authToken, Integer bannerId) {
        this.authToken = authToken;
        this.bannerId = bannerId;
    }

    @Override
    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<authToken>").append(this.authToken).append("</authToken>\n");
        xmlBuilder.append("<bannerId>").append(this.bannerId).append("</bannerId>\n");
        return xmlBuilder.toString();
    }

    public ServicePayloadBean getServicePayload() {
        return new ServicePayloadBean(authToken);
    }
}
