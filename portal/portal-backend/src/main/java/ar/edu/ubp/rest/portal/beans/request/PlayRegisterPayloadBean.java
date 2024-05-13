package ar.edu.ubp.rest.portal.beans.request;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayRegisterPayloadBean {
    private Integer sessionId;
    private Date playedAt;
    private Integer subscriberId;
    private Integer transactionId;
    private String filmCode;
    private String sessionUrl;

    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String playedAt = dateFormater.format(this.playedAt);

        xmlBuilder.append("<reproductions>");
        xmlBuilder.append("<sessionId>").append(this.sessionId).append("</sessionId>\n");
        xmlBuilder.append("<subscriberId>").append(this.subscriberId).append("</subscriberId>\n");
        xmlBuilder.append("<transactionId>").append(this.transactionId).append("</transactionId>\n");
        xmlBuilder.append("<filmCode>").append(this.filmCode).append("</filmCode>\n");
        xmlBuilder.append("<sessionUrl>").append(this.sessionUrl).append("</sessionUrl>\n");
        xmlBuilder.append("<playedAt>").append(playedAt).append("</playedAt>\n");
        xmlBuilder.append("</reproductions>");

        return xmlBuilder.toString();
    }

}
