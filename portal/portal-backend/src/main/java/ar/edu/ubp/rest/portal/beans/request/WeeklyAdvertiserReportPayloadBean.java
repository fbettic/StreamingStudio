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
public class WeeklyAdvertiserReportPayloadBean extends AbstractServicePayload {
    private Integer reportId;
    private Date fromDate;
    private Date toDate;
    private List<AdvertisingClickReportBean> clicks;

    @Builder
    public WeeklyAdvertiserReportPayloadBean(
            String authToken,
            Integer reportId,
            Date fromDate,
            Date toDate,
            List<AdvertisingClickReportBean> clicks) {
        this.authToken = authToken;
        this.reportId = reportId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.clicks = clicks;
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

        for (AdvertisingClickReportBean click : clicks) {
            xmlBuilder.append(click.toSoapXml());
        }

        xmlBuilder.append("</report>");

        return xmlBuilder.toString();
    }
}
