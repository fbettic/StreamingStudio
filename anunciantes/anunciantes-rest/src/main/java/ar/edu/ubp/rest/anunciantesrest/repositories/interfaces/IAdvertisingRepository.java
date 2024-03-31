package ar.edu.ubp.rest.anunciantesrest.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.anunciantesrest.beans.AdvertisingBean;


public interface IAdvertisingRepository {
    
    public AdvertisingBean getAdvertisingById(Integer id);
    public List<AdvertisingBean> getAllAdvertisings(Integer serviceId);
    
}
