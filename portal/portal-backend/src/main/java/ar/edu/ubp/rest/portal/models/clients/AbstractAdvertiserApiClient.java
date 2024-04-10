package ar.edu.ubp.rest.portal.models.clients;

import java.util.List;

import ar.edu.ubp.rest.portal.beans.request.BasicPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BannerResponseBean;
import lombok.Data;

@Data
public abstract class AbstractAdvertiserApiClient {
    protected String url = "";
    
    public abstract String ping(BasicPayloadBean payload);

    public abstract BannerResponseBean getBannerById(Integer id, BasicPayloadBean payload);

    public abstract List<AdvertisingResponseBean> getAllAdvertisings(BasicPayloadBean payload);


}
