package ar.edu.ubp.rest.portal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public TargetCategoryDTO createTargetCategory(String targetTitle) {
        return targetRepository.createTargetCategory(targetTitle);
    }

    public TargetCategoryDTO updateTargetCategory(Integer id, String targetTitle) {
        return targetRepository.updateTargetCategory(id, targetTitle);
    }

    public List<TargetCategoryDTO> getAllTargetCategories() {
        return targetRepository.getAllTargetCategories();
    }

    public Integer deleteTargetCategory(Integer id) {
        return targetRepository.deleteTargetCategory(id);
    }

    public List<AdvertisingTargetDTO> addAllAdvertisingTarget(List<Integer> targets, Integer advertisingId) {

        Integer deleted = targetRepository.removeAllAdvertisingTarget(advertisingId);

        List<AdvertisingTargetDTO> result = new ArrayList<>();

        if (deleted.intValue() == advertisingId.intValue()) {

            targets.forEach((targetId) -> {
                AdvertisingTargetDTO target = targetRepository.addAdvertisingTarget(targetId, advertisingId);
                if (Objects.isNull(target)) {
                    result.add(target);
                }
            });

        }

        return result;
    }

    public AdvertisingTargetDTO removeAdvertisingTarget(Integer targetId, Integer advertisingId) {
        return targetRepository.removeAdvertisingTarget(targetId, advertisingId);
    }

    public List<TargetCategoryDTO> getAllAdvertisingTargetByAdvertisingId(Integer advertisingId) {
        return targetRepository.getAllAdvertisingTargetByAdvertisingId(advertisingId);
    }

    public MarketingPreferenceDTO addMarketingPreference(Integer targetId, Integer subscriberId) {
        return targetRepository.addMarketingPreference(targetId, subscriberId);
    }

    public List<MarketingPreferenceDTO> addAllMarketingPreference(List<Integer> targets, Integer subscriberId) {

        Integer deleted = targetRepository.removeAllMarketingPreference(subscriberId);

        List<MarketingPreferenceDTO> result = new ArrayList<>();

        if (deleted.intValue() == subscriberId.intValue()) {

            targets.forEach((targetId) -> {
                MarketingPreferenceDTO target = targetRepository.addMarketingPreference(targetId, subscriberId);
                if (Objects.isNull(target)) {
                    result.add(target);
                }
            });

        }
        return result;
    }

    public MarketingPreferenceDTO removeMarketingPreference(Integer targetId, Integer subscriberId) {
        return targetRepository.removeMarketingPreference(targetId, subscriberId);
    }

    public List<TargetCategoryDTO> getAllMarketingPreferencesBySubscriberId(Integer subscriberId) {
        return targetRepository.getAllMarketingPreferencesBySubscriberId(subscriberId);
    }
}
