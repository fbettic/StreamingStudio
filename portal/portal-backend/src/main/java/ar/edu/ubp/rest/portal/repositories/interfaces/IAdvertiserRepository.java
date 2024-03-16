package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.models.Advertiser;

public interface IAdvertiserRepository {
    public AdvertiserDTO save(Advertiser advertiser);
    
    public AdvertiserDTO findAdvertiserByEmail(String email);

    public List<AdvertiserDTO> findAdvertisers();

    public Integer deleteAdvertiserById(Integer id);

    public AdvertiserDTO updateAdvertiserById(AdvertiserRequestDTO advertiser, Integer id);

}
