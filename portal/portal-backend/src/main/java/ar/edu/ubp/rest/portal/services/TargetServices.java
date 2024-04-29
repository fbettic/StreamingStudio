package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.dto.AdvertisingTargetDTO;
import ar.edu.ubp.rest.portal.dto.MarketingPreferenceDTO;
import ar.edu.ubp.rest.portal.dto.TargetCategoryDTO;
import ar.edu.ubp.rest.portal.repositories.TargetRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TargetServices {
    private final TargetRepository targetRepository;

    public TargetCategoryDTO createTargetCategory(String targetTitle){
        return targetRepository.createTargetCategory(targetTitle);
    }

    public TargetCategoryDTO updateTargetCategoryTitle(Integer id, String targetTitle){
        return targetRepository.updateTargetCategoryTitle(id, targetTitle);
    }

    public List<TargetCategoryDTO> getAllTargetCategories(){
        return targetRepository.getAllTargetCategories();
    }

    public AdvertisingTargetDTO addAdvertisingTarget(Integer targetId, Integer advertisingId){
        return targetRepository.addAdvertisingTarget(targetId, advertisingId);
    }

    public AdvertisingTargetDTO removeAdvertisingTarget(Integer targetId, Integer advertisingId){
        return targetRepository.removeAdvertisingTarget(targetId, advertisingId);
    }

    public List<TargetCategoryDTO> getAllAdvertisingTargetByAdvertisingId(Integer advertisingId){
        return targetRepository.getAllAdvertisingTargetByAdvertisingId(advertisingId);
    }

    public MarketingPreferenceDTO addMarketingPreference(Integer targetId, Integer subscriberId){
        return targetRepository.addMarketingPreference(targetId, subscriberId);
    }

    public MarketingPreferenceDTO removeMarketingPreference(Integer targetId, Integer subscriberId){
        return targetRepository.removeMarketingPreference(targetId, subscriberId);
    }

    public List<TargetCategoryDTO> getAllAdvertisingTargetBySubscriberId(Integer subscriberId){
        return targetRepository.getAllAdvertisingTargetBySubscriberId(subscriberId);
    }
}
