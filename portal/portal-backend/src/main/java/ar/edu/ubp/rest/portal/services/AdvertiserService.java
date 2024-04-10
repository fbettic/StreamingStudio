package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.response.BannerResponseBean;
import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.repositories.AdvertiserRepository;
import ar.edu.ubp.rest.portal.repositories.AdvertisingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertiserService {

    // Repositories
    @Autowired
    private final AdvertisingRepository advertisingRepository;

    @Autowired
    private final AdvertiserRepository advertiserRepository;
    // Services
    @Autowired
    private final AdvertiserApiClientService advertiserApiClientService;

    public AdvertiserDTO updateAdvertiserById(Integer id, AdvertiserRequestDTO advertiserRequest) {
        return advertiserRepository.updateAdvertiserById(advertiserRequest, id);
    }

    public AdvertisingDTO createAdvertising(AdvertisingRequestDTO advertisingRequest) throws Exception  {
    
        advertisingRequest.setBannerId(null);

        return advertisingRepository.createAdvertising(advertisingRequest);
    }

    public AdvertisingDTO getAdvertisingById(Integer id) {
        return advertisingRepository.getAdvertisingById(id);
    }

    public List<AdvertisingDTO> getAllAdvertisingsByAdvertiser(Integer id) {
        return advertisingRepository.getAllAdvertisingsByAdvertiser(id);
    }

    public AdvertisingDTO updateAdvertisingById(Integer id, AdvertisingRequestDTO advertisingRequest) {
        return advertisingRepository.updateAdvertisingById(id, advertisingRequest);
    }

    public Integer deleteAdvertisingById(Integer id) {
        return advertisingRepository.deleteAdvertisingById(id);
    }
}
