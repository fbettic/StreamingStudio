package ar.edu.ubp.rest.portal.beans.request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyPlatformReportPayloadBean extends AbstractServicePayload {
    private Integer reportId;
    private Date fromDate;
    private Date toDate;
    private List<PlayRegisterPayloadBean> reproductions;
    private List<AssociationReportPayloadBean> associations;

    @Builder
    public WeeklyPlatformReportPayloadBean(
            String authToken,
            Integer reportId,
            Date fromDate,
            Date toDate,
            List<PlayRegisterPayloadBean> reproductions,
            List<AssociationReportPayloadBean> associations) {
        this.authToken = authToken;
        this.reportId = reportId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reproductions = reproductions;
        this.associations = associations;
    }

    @Override
    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String fromDate = dateFormater.format(this.fromDate);
        String toDate = dateFormater.format(this.toDate);

        xmlBuilder.append("<report>");
        xmlBuilder.append("<authToken>").append(this.authToken).append("</authToken>\n");
        xmlBuilder.append("<reportId>").append(this.reportId).append("</reportId>\n");
        xmlBuilder.append("<fromDate>").append(fromDate).append("</fromDate>\n");
        xmlBuilder.append("<toDate>").append(toDate).append("</toDate>\n");

        for (PlayRegisterPayloadBean reproduction : reproductions) {
            xmlBuilder.append(reproduction.toSoapXml());
        }

        for (AssociationReportPayloadBean association : associations) {
            xmlBuilder.append(association.toSoapXml());
        }

        xmlBuilder.append("</report>");

        return xmlBuilder.toString();
    }

}
