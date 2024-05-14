package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.BannerDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingClickRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.SubscriberAdvertisingDTO;

public interface IAdvertisingRepository {
    public AdvertisingDTO createAdvertising(AdvertisingRequestDTO advertising);

    public List<AdvertisingDTO> getAllAdvertisings();

    public String updateBatchBanner(List<BannerDTO> banners);

    public List<AdvertisingDTO> getAllAdvertisingsByAdvertiser(Integer advertiserId);

    public List<SubscriberAdvertisingDTO> getAllAdvertisingForSubscriber(Integer subscriberId);

    public String createSubscriberAdvertisingClick(Integer subscriberId, AdvertisingClickRequestDTO click);

    public AdvertisingDTO getAdvertisingById(Integer id);

    public AdvertisingDTO updateAdvertisingById(Integer id, AdvertisingRequestDTO advertising);

    public Integer deleteAdvertisingById(Integer id);
}
