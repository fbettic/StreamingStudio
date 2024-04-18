package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.BannerPriorityDTO;
import ar.edu.ubp.rest.portal.dto.request.BannerPriorityRequestDTO;

public interface IBannerPriorityRepository {
    public BannerPriorityDTO createBannerPriority(BannerPriorityRequestDTO priority);
    public BannerPriorityDTO updateBannerPriority(BannerPriorityRequestDTO priority, Integer id);
    public BannerPriorityDTO getBannerPriorityById(Integer id);
    public List<BannerPriorityDTO> getAllBannerPriorities();
    public Integer deleteBannerPriorityById(Integer id);
}
