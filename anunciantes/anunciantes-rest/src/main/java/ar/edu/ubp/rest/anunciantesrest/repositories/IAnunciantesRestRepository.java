package ar.edu.ubp.rest.anunciantesrest.repositories;

import java.util.List;

import ar.edu.ubp.rest.anunciantesrest.beans.AdvertisingWithBannerBean;
import ar.edu.ubp.rest.anunciantesrest.beans.BannerBean;

public interface IAnunciantesRestRepository {
    // public List<AdvertisingWithBannerBean> getAllAdvertisingsWithBanners();
    // public AdvertisingWithBannerBean getAdvertisingWithBanner(int advertisingId);
    public BannerBean getBanner(int bannerId);
}