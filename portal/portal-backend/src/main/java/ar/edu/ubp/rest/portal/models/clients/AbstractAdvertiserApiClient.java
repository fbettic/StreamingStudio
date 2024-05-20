package ar.edu.ubp.rest.portal.models.clients;

import java.util.List;

import ar.edu.ubp.rest.portal.beans.request.AdvertisingPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.ServicePayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyAdvertiserReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BasicResponseBean;
import lombok.Data;

@Data
public abstract class AbstractAdvertiserApiClient {
    protected String url = "";

    public abstract BasicResponseBean ping(ServicePayloadBean payload);

    public abstract AdvertisingResponseBean getAdvertisingById(AdvertisingPayloadBean payload);

    public abstract List<AdvertisingResponseBean> getAllAdvertisings(ServicePayloadBean payload);

    public abstract BasicResponseBean sendWeeklyReport(WeeklyAdvertiserReportPayloadBean payload);
}
