package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.AdvertisingTargetDTO;
import ar.edu.ubp.rest.portal.dto.MarketingPreferenceDTO;
import ar.edu.ubp.rest.portal.dto.TargetCategoryDTO;

public interface ITargetRepository {

    public TargetCategoryDTO createTargetCategory(String targetTitle);

    public TargetCategoryDTO updateTargetCategoryTitle(Integer id, String targetTitle);

    public List<TargetCategoryDTO> getAllTargetCategories();

    public AdvertisingTargetDTO addAdvertisingTarget(Integer targetId, Integer advertisingId);

    public AdvertisingTargetDTO removeAdvertisingTarget(Integer targetId, Integer advertisingId);

    public List<TargetCategoryDTO> getAllAdvertisingTargetByAdvertisingId(Integer advertisingId);

    public MarketingPreferenceDTO addMarketingPreference(Integer targetId, Integer subscriberId);

    public MarketingPreferenceDTO removeMarketingPreference(Integer targetId, Integer subscriberId);

    public List<TargetCategoryDTO> getAllAdvertisingTargetBySubscriberId(Integer subscriberId);

}
