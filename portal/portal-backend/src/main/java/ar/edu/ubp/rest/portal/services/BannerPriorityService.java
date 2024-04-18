package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.dto.BannerPriorityDTO;
import ar.edu.ubp.rest.portal.dto.request.BannerPriorityRequestDTO;
import ar.edu.ubp.rest.portal.repositories.BannerPriorityRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BannerPriorityService {

    @Autowired
    private final BannerPriorityRepository bannerPriorityRepository;

    public BannerPriorityDTO createBannerPriority(BannerPriorityRequestDTO priority) {
        return bannerPriorityRepository.createBannerPriority(priority);
    }

    public BannerPriorityDTO updateBannerPriority(BannerPriorityRequestDTO priority, Integer id) {
        return bannerPriorityRepository.updateBannerPriority(priority, id);
    }

    public BannerPriorityDTO getBannerPriorityById(Integer id) {
        return bannerPriorityRepository.getBannerPriorityById(id);
    }

    public List<BannerPriorityDTO> getAllBannerPriorities() {
        return bannerPriorityRepository.getAllBannerPriorities();
    }

    public Integer deleteBannerPriorityById(Integer id) {
        return bannerPriorityRepository.deleteBannerPriorityById(id);
    }
}
