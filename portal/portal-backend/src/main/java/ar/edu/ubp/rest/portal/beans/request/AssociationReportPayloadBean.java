package ar.edu.ubp.rest.portal.beans.request;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociationReportPayloadBean {
    private Integer subscriberId;
    private Integer transactionId;
    private String associationType;
    private String authUrl;
    private Date entryDate;
    private Date leavingDate;

    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String entryDateStr = dateFormatter.format(this.entryDate);
        String leavingDateStr = this.leavingDate != null ? dateFormatter.format(this.leavingDate) : null;

        xmlBuilder.append("<associations>\n");
        xmlBuilder.append("<subscriberId>").append(this.subscriberId).append("</subscriberId>\n");
        xmlBuilder.append("<transactionId>").append(this.transactionId).append("</transactionId>\n");
        xmlBuilder.append("<associationType>").append(this.associationType).append("</associationType>\n");
        xmlBuilder.append("<authUrl>").append(this.authUrl).append("</authUrl>\n");
        xmlBuilder.append("<entryDate>").append(entryDateStr).append("</entryDate>\n");
        if (leavingDateStr != null) {
            xmlBuilder.append("<leavingDate>").append(leavingDateStr).append("</leavingDate>\n");
        }
        xmlBuilder.append("</associations>");

        return xmlBuilder.toString();
    }

}
