package ar.edu.ubp.soap.beans;

public class AdvertisingWithBannerBean {
    private AdvertisingBean advertising;
    private BannerBean banner;

    public AdvertisingWithBannerBean(AdvertisingBean advertising, BannerBean banner) {
        this.advertising = advertising;
        this.banner = banner;
    }

    public AdvertisingBean getAdvertising() {
        return advertising;
    }

    public void setAdvertising(AdvertisingBean advertising) {
        this.advertising = advertising;
    }

    public BannerBean getBanner() {
        return banner;
    }

    public void setBanner(BannerBean banner) {
        this.banner = banner;
    }
}
