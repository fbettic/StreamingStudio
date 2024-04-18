package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.response.BannerResponseBean;
import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.repositories.AdvertisingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertisingService {
    @Autowired
    private final AdvertisingRepository advertisingRepository;

    @Autowired
    private final AdvertiserApiClientService advertiserApiClientService;

    @Autowired
    private final BatchService batchService;

    // Advertisings
    public AdvertisingDTO createAdvertising(AdvertisingRequestDTO advertisingRequest) {
        if (advertisingRequest.getBannerId() != null && advertisingRequest.getBannerId() > 0) {
            BannerResponseBean bannerData = advertiserApiClientService.getBannerById(advertisingRequest.getBannerId(),
                    advertisingRequest.getAdvertiserId());

            advertisingRequest.setBannerText(bannerData.getText());
            advertisingRequest.setImageUrl(bannerData.getImageUrl());
            advertisingRequest.setRedirectUrl(bannerData.getRedirectUrl());
        }

        return advertisingRepository.createAdvertising(advertisingRequest);
    }

    public AdvertisingDTO updateAdvertisingById(Integer id, AdvertisingRequestDTO advertisingRequest) {
        return advertisingRepository.updateAdvertisingById(id, advertisingRequest);
    }

    public AdvertisingDTO getAdvertisingById(Integer id) {
        return advertisingRepository.getAdvertisingById(id);
    }

    public List<AdvertisingDTO> getAllAdvertisingsByAdvertiser(Integer id) {
        return advertisingRepository.getAllAdvertisingsByAdvertiser(id);
    }

    public String getAllAdvertisingsFromAdvertisers() throws Exception {
        try {
            batchService.updateAdvertisings(advertiserApiClientService.getAllAdvertisingsFromAdvertisers());
        } catch (Exception e) {
            throw new Exception(e);
        }
        return "Advertisings have been updated";
    }

    public List<AdvertisingDTO> getAllAdvertisings() {
        return advertisingRepository.getAllAdvertisings();
    }

    public Integer deleteAdvertisingById(Integer id) {
        return advertisingRepository.deleteAdvertisingById(id);
    }
}
