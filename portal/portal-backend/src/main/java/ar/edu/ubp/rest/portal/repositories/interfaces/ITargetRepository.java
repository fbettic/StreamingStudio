package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.TargetCategoryDTO;

public interface ITargetRepository {

    public TargetCategoryDTO createTargetCategory(String targetTitle);

    public TargetCategoryDTO updateTargetCategory(Integer id, String targetTitle);

    public Integer deleteTargetCategory(Integer id);

    public List<TargetCategoryDTO> getAllTargetCategories();

    public String updateAdvertisingTargetsFromJson(String json);

    public List<TargetCategoryDTO> getAllAdvertisingTargetByAdvertisingId(Integer advertisingId);

    public String updateMarketingPreferencesFromJson(String json);

    public List<TargetCategoryDTO> getAllMarketingPreferencesBySubscriberId(Integer subscriberId);

}
