package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.models.users.Advertiser;

public interface IAdvertiserRepository {
    public AdvertiserDTO createAdvertiser(Advertiser advertiser);

    public List<AdvertiserDTO> getAllAdvertisers();

    public AdvertiserDTO getAdvertiserById(Integer id);

    public Integer deleteAdvertiserById(Integer id);

    public AdvertiserDTO updateAdvertiserById(AdvertiserRequestDTO advertiser, Integer id);

}
