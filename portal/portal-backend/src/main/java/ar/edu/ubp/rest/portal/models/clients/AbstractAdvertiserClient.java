package ar.edu.ubp.rest.portal.models.clients;

import java.util.List;

import ar.edu.ubp.rest.portal.beans.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.AuthTokenRequestBean;
import ar.edu.ubp.rest.portal.beans.BannerResponseBean;
import lombok.Data;

@Data
public abstract class AbstractAdvertiserClient {
    protected String url = "";
    
    public abstract String ping(AuthTokenRequestBean authToken);

    public abstract BannerResponseBean getBannerById(Integer id, AuthTokenRequestBean authToken);

    public abstract List<AdvertisingResponseBean> getAllAdvertisings(AuthTokenRequestBean authToken);


}
