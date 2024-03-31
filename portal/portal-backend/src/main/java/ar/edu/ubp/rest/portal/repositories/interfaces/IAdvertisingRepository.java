package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.BannerUpdateDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;

public interface IAdvertisingRepository {
    public AdvertisingDTO createAdvertising(AdvertisingRequestDTO advertising);

    public List<AdvertisingDTO> getAllAdvertisings();

    public Integer updateBatchBanner(List<BannerUpdateDTO> banners);

    //public AdvertisingDTO getAdvertisingById(Integer id);

    //public Integer deleteAdvertisingById(Integer id);
}
