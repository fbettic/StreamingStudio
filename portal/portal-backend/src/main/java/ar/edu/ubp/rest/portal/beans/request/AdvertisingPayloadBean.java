package ar.edu.ubp.rest.portal.beans.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AdvertisingPayloadBean extends AbstractServicePayload {
    private Integer advertisingId;

    public AdvertisingPayloadBean(String authToken, Integer advertisingId) {
        this.authToken = authToken;
        this.advertisingId = advertisingId;
    }

    @Override
    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<authToken>").append(this.authToken).append("</authToken>\n");
        xmlBuilder.append("<advertisingId>").append(this.advertisingId).append("</advertisingId>\n");
        return xmlBuilder.toString();
    }

    public ServicePayloadBean getServicePayload() {
        return new ServicePayloadBean(authToken);
    }
}
