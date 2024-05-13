package ar.edu.ubp.rest.portal.beans.request;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisingClickReportBean {
    private Integer clickId;
    private Integer bannerId;
    private Integer subscriberId;
    private Date clickedAt;

    public String toSoapXml() {
        StringBuilder xmlBuilder = new StringBuilder();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String clickedAt = dateFormater.format(this.clickedAt);

        xmlBuilder.append("<clicks>");
        xmlBuilder.append("<clickId>").append(this.clickId).append("</clickId>\n");
        xmlBuilder.append("<subscriberId>").append(this.subscriberId).append("</subscriberId>\n");
        xmlBuilder.append("<bannerId>").append(this.bannerId).append("</bannerId>\n");
        xmlBuilder.append("<clickedAt>").append(clickedAt).append("</clickedAt>\n");
        xmlBuilder.append("</clicks>");

        return xmlBuilder.toString();
    }
}
