package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.rest.portal.dto.TargetCategoryDTO;
import ar.edu.ubp.rest.portal.dto.TargetMatchedDTO;
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

    public String updateAdvertisingTargetsFromJson(List<Integer> targets, Integer advertisingId)
            throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(new TargetMatchedDTO(advertisingId, targets));
        } catch (JsonProcessingException e) {
            throw e;
        }

        return targetRepository.updateAdvertisingTargetsFromJson(json);
    }

    public List<TargetCategoryDTO> getAllAdvertisingTargetByAdvertisingId(Integer advertisingId) {
        return targetRepository.getAllAdvertisingTargetByAdvertisingId(advertisingId);
    }

    public String updateMarketingPreferencesFromJson(List<Integer> targets, Integer subscriberId)
            throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(new TargetMatchedDTO(subscriberId, targets));
        } catch (JsonProcessingException e) {
            throw e;
        }

        return targetRepository.updateMarketingPreferencesFromJson(json);
    }

    public List<TargetCategoryDTO> getAllMarketingPreferencesBySubscriberId(Integer subscriberId) {
        return targetRepository.getAllMarketingPreferencesBySubscriberId(subscriberId);
    }
}
