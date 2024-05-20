package ar.edu.ubp.rest.portal.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.BannerPriorityDTO;
import ar.edu.ubp.rest.portal.dto.SizeTypeDTO;
import ar.edu.ubp.rest.portal.dto.TargetCategoryDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingClickRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.BannerPriorityRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SizeTypeRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SubscriberAdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.SubscriberAdvertisingResponseDTO;
import ar.edu.ubp.rest.portal.enums.Role;
import ar.edu.ubp.rest.portal.services.AdvertisingService;
import ar.edu.ubp.rest.portal.services.BannerPriorityService;
import ar.edu.ubp.rest.portal.services.CustomUserDetailsService;
import ar.edu.ubp.rest.portal.services.SizeTypeService;
import ar.edu.ubp.rest.portal.services.TargetServices;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/advertisings")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AdvertisingController {
    @Autowired
    private final AdvertisingService advertisingService;
    @Autowired
    private final SizeTypeService sizeTypeService;
    @Autowired
    private final BannerPriorityService bannerPriorityService;
    @Autowired
    private final TargetServices targetServices;

    @Autowired
    private final CustomUserDetailsService userService;

    @PostMapping("")
    public ResponseEntity<AdvertisingDTO> createAdvertising(@RequestBody AdvertisingRequestDTO advertisingRequest)
            throws Exception {

        if (userService.getCurrentRole().equals(Role.ADVERTISER)) {
            Integer userId = userService.getCurrentUserId();
            advertisingRequest.setAdvertiserId(userId);
        }

        return ResponseEntity.ok(advertisingService.createAdvertising(advertisingRequest));
    }

    @GetMapping("")
    public ResponseEntity<List<AdvertisingDTO>> getAllAdvertisings() throws Exception {
        if (userService.getCurrentRole().equals(Role.ADVERTISER)) {
            Integer userId = userService.getCurrentUserId();
            return ResponseEntity.ok(advertisingService.getAllAdvertisingsByAdvertiser(userId));
        }
        return ResponseEntity.ok(advertisingService.getAllAdvertisings());

    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisingDTO> getAdvertisingById(@PathVariable Integer id) throws Exception {
        AdvertisingDTO response = advertisingService.getAdvertisingById(id);

        if (!userService.getCurrentRole().equals(Role.ADMINISTRATOR)
                && !response.getAdvertiserId().equals(userService.getCurrentUserId())) {
            return ResponseEntity.notFound().build();
        }

        List<TargetCategoryDTO> targets = targetServices.getAllAdvertisingTargetByAdvertisingId(id);

        List<Integer> targetsId = new ArrayList<>();
        if (Objects.nonNull(targets)) {
            for (TargetCategoryDTO target : targets) {
                targetsId.add(target.getTargetId());
            }
        }

        response.setTargets(targetsId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisingDTO> updateAdvertisingById(@PathVariable Integer id,
            @RequestBody AdvertisingRequestDTO advertisingRequest) throws Exception {

        AdvertisingDTO advertising = advertisingService.getAdvertisingById(id);

        if (!userService.getCurrentRole().equals(Role.ADMINISTRATOR)
                && !advertising.getAdvertiserId().equals(userService.getCurrentUserId())) {
            return ResponseEntity.notFound().build();
        }

        AdvertisingDTO response = advertisingService.updateAdvertisingById(id, advertisingRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteAdvertisingsById(@PathVariable Integer id) throws Exception {
        AdvertisingDTO advertising = advertisingService.getAdvertisingById(id);

        if (!userService.getCurrentRole().equals(Role.ADMINISTRATOR)
                && !advertising.getAdvertiserId().equals(userService.getCurrentUserId())) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(advertisingService.deleteAdvertisingById(id));
    }

    @PostMapping("/subscriber")
    public ResponseEntity<List<SubscriberAdvertisingResponseDTO>> getAdvertisingForSubscriber(
            @RequestParam("allpages") Boolean allPages,
            @RequestBody List<SubscriberAdvertisingRequestDTO> request) throws Exception {

        return ResponseEntity.ok(advertisingService.getAdvertisingsForSubscriber(allPages,
                userService.getCurrentUserId(), request));
    }

    @PostMapping("/track")
    public ResponseEntity<String> createSubscriberAdvertisingClick(
            @RequestBody AdvertisingClickRequestDTO request) throws Exception {

        return ResponseEntity
                .ok(advertisingService.createSubscriberAdvertisingClick(userService.getCurrentUserId(), request));
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

    @GetMapping("{id}/targets")
    public ResponseEntity<List<TargetCategoryDTO>> getAllAdvertisingTargetByAdvertisingId(@PathVariable Integer id) {
        return ResponseEntity.ok(targetServices.getAllAdvertisingTargetByAdvertisingId(id));
    }

}
