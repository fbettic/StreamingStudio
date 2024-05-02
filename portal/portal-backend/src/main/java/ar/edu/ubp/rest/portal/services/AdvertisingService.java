package ar.edu.ubp.rest.portal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.response.BannerResponseBean;
import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.TargetCategoryDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SubscriberAdvertisingRequestDTO;
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

    @Autowired
    private final TargetServices targetServices;

    // Advertisings
    public AdvertisingDTO createAdvertising(AdvertisingRequestDTO advertisingRequest) {
        if (advertisingRequest.getBannerId() != null && advertisingRequest.getBannerId() > 0) {
            BannerResponseBean bannerData = advertiserApiClientService.getBannerById(advertisingRequest.getBannerId(),
                    advertisingRequest.getAdvertiserId());

            advertisingRequest.setBannerText(bannerData.getText());
            advertisingRequest.setImageUrl(bannerData.getImageUrl());
            advertisingRequest.setRedirectUrl(bannerData.getRedirectUrl());
        }

        AdvertisingDTO response = advertisingRepository.createAdvertising(advertisingRequest);

        if (!Objects.isNull(response)) {
            targetServices.addAllAdvertisingTarget(advertisingRequest.getTargets(), response.getAdvertisingId());
        }
        return response;
    }

    public AdvertisingDTO updateAdvertisingById(Integer id, AdvertisingRequestDTO advertisingRequest) {
        if (advertisingRequest.getBannerId() != null && advertisingRequest.getBannerId() > 0) {
            BannerResponseBean bannerData = advertiserApiClientService.getBannerById(advertisingRequest.getBannerId(),
                    advertisingRequest.getAdvertiserId());

            advertisingRequest.setBannerText(bannerData.getText());
            advertisingRequest.setImageUrl(bannerData.getImageUrl());
            advertisingRequest.setRedirectUrl(bannerData.getRedirectUrl());
        }

        AdvertisingDTO response = advertisingRepository.updateAdvertisingById(id, advertisingRequest);

        if (!Objects.isNull(response)) {
            targetServices.addAllAdvertisingTarget(advertisingRequest.getTargets(), response.getAdvertisingId());
        }
        return response;
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

    public List<SubscriberAdvertisingRequestDTO> getAdvertisingsToShow(Integer subscriberId,
            List<SubscriberAdvertisingRequestDTO> request) {
        List<AdvertisingDTO> advertisings = advertisingRepository.getAllAdvertisings();
        List<TargetCategoryDTO> marketingPreferences = targetServices
                .getAllMarketingPreferencesBySubscriberId(subscriberId);

        for (SubscriberAdvertisingRequestDTO requestSize : request) {
            AdvertisingDTO mostMatched = null;
            int maxMatched = 0;
            for (AdvertisingDTO advertising : advertisings) {
                if (requestSize.getSizeType().equals(advertising.getSizeType())) {
                    List<TargetCategoryDTO> targets = targetServices
                            .getAllAdvertisingTargetByAdvertisingId(advertising.getAdvertisingId());

                    List<Integer> targetsId = new ArrayList<>();
                    if (Objects.nonNull(targets)) {
                        for (TargetCategoryDTO target : targets) {
                            targetsId.add(target.getTargetId());
                        }
                    }

                    advertising.setTargets(targetsId);

                    int count = 0;
                    for (TargetCategoryDTO target : marketingPreferences) {
                        if (advertising.getTargets().contains(target.getTargetId())) {
                            count++;
                        }
                    }
                    if (count > maxMatched
                            || count == maxMatched && advertising.getPriorityValue() > mostMatched.getPriorityValue() ||
                            Objects.isNull(mostMatched)) {
                        mostMatched = advertising;
                        maxMatched = count;
                    } else if (count == maxMatched
                            && advertising.getPriorityValue().equals(mostMatched.getPriorityValue())) {
                        Random random = new Random();
                        if (random.nextBoolean()) {
                            mostMatched = advertising;
                            maxMatched = count;
                        }
                    }
                }
            }
            requestSize.setAdvertising(mostMatched);
        }

        return request;
    }

    public Integer deleteAdvertisingById(Integer id) {
        return advertisingRepository.deleteAdvertisingById(id);
    }
}
