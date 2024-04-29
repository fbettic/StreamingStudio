package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.BannerPriorityDTO;
import ar.edu.ubp.rest.portal.dto.SizeTypeDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.BannerPriorityRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SizeTypeRequestDTO;
import ar.edu.ubp.rest.portal.enums.Role;
import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;
import ar.edu.ubp.rest.portal.services.AdvertisingService;
import ar.edu.ubp.rest.portal.services.BannerPriorityService;
import ar.edu.ubp.rest.portal.services.SizeTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/advertisings")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AdvertisingController {

    private final AdvertisingService advertisingService;

    private final SizeTypeService sizeTypeService;

    private final BannerPriorityService bannerPriorityService;

    private Integer getCurrentUserId() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Integer userId = userDetails.getId();
            return userId;
        } else {
            throw new Exception("User id not found");
        }
    }

    private Role getCurrentRole() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Role userId = userDetails.getRole();
            return userId;
        } else {
            throw new Exception("User role not found");
        }
    }

    @PostMapping("")
    public ResponseEntity<AdvertisingDTO> createAdvertising(@RequestBody AdvertisingRequestDTO advertisingRequest)
            throws Exception {

        

        if (getCurrentRole().name().equals("ADVERTISER")) {
            Integer userId = getCurrentUserId();

            

            advertisingRequest.setAdvertiserId(userId);
        }

        return ResponseEntity.ok(advertisingService.createAdvertising(advertisingRequest));
    }

    @GetMapping("")
    public ResponseEntity<List<AdvertisingDTO>> getAllAdvertisings() throws Exception {
        if (getCurrentRole().name().equals("ADVERTISER")) {
            Integer userId = getCurrentUserId();
            return ResponseEntity.ok(advertisingService.getAllAdvertisingsByAdvertiser(userId));
        } else {
            return ResponseEntity.ok(advertisingService.getAllAdvertisings());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisingDTO> getAdvertisingById(@PathVariable Integer id) throws Exception {
        AdvertisingDTO response = advertisingService.getAdvertisingById(id);

        if (!getCurrentRole().name().equals("ADMINISTRATOR")
                && !response.getAdvertiserId().equals(getCurrentUserId())) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisingDTO> updateAdvertisingById(@PathVariable Integer id,
            @RequestBody AdvertisingRequestDTO advertisingRequest) throws Exception {

        AdvertisingDTO advertising = advertisingService.getAdvertisingById(id);
        Integer userId = getCurrentUserId();

        if (!getCurrentRole().name().equals("ADMINISTRATOR")
                && !advertising.getAdvertiserId().equals(getCurrentUserId())) {
            return ResponseEntity.notFound().build();
        }

        advertisingRequest.setAdvertiserId(userId);

        AdvertisingDTO response = advertisingService.updateAdvertisingById(id, advertisingRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteAdvertisingsById(@PathVariable Integer id) throws Exception {
        AdvertisingDTO advertising = advertisingService.getAdvertisingById(id);
        System.out.println(advertising.getAdvertiserId().equals(getCurrentUserId()));
        if (!getCurrentRole().name().equals("ADMINISTRATOR")
                && !advertising.getAdvertiserId().equals(getCurrentUserId())) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(advertisingService.deleteAdvertisingById(id));
    }

    @PostMapping("/sizes")
    public ResponseEntity<SizeTypeDTO> createSizeType(@RequestBody SizeTypeRequestDTO sizeType)
            throws Exception {

        return ResponseEntity.ok(sizeTypeService.createSizeType(sizeType));
    }

    @GetMapping("/sizes")
    public ResponseEntity<List<SizeTypeDTO>> getAllSizeTypes() throws Exception {
        return ResponseEntity.ok(sizeTypeService.getAllSizeTypes());
    }

    @GetMapping("/sizes/{id}")
    public ResponseEntity<SizeTypeDTO> getSizeTypeById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(sizeTypeService.getSizeTypeById(id));
    }

    @PutMapping("/sizes/{id}")
    public ResponseEntity<SizeTypeDTO> updateSizeType(@PathVariable Integer id,
            @RequestBody SizeTypeRequestDTO sizeTypeRequest) throws Exception {
        return ResponseEntity.ok(sizeTypeService.updateSizeType(sizeTypeRequest, id));
    }

    @DeleteMapping("/sizes/{id}")
    public ResponseEntity<Integer> deleteSizeTypeById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(sizeTypeService.deleteSizeTypeById(id));
    }

    @PostMapping("/priorities")
    public ResponseEntity<BannerPriorityDTO> createBannerPriority(
            @RequestBody BannerPriorityRequestDTO bannerPriorityRequest)
            throws Exception {

        return ResponseEntity.ok(bannerPriorityService.createBannerPriority(bannerPriorityRequest));
    }

    @GetMapping("/priorities")
    public ResponseEntity<List<BannerPriorityDTO>> getAllBannerPriorities() throws Exception {
        return ResponseEntity.ok(bannerPriorityService.getAllBannerPriorities());
    }

    @GetMapping("/priorities/{id}")
    public ResponseEntity<BannerPriorityDTO> getBannerPriorityById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(bannerPriorityService.getBannerPriorityById(id));
    }

    @PutMapping("/priorities/{id}")
    public ResponseEntity<BannerPriorityDTO> updateBannerPriority(@PathVariable Integer id,
            @RequestBody BannerPriorityRequestDTO sizeTypeRequest) throws Exception {
        return ResponseEntity.ok(bannerPriorityService.updateBannerPriority(sizeTypeRequest, id));
    }

    @DeleteMapping("/priorities/{id}")
    public ResponseEntity<Integer> deleteBannerPriorityById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(bannerPriorityService.deleteBannerPriorityById(id));
    }
    
    
}
