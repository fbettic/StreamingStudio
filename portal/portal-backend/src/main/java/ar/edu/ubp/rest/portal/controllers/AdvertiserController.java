package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;
import ar.edu.ubp.rest.portal.services.AdvertiserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/advertiser/")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AdvertiserController {
    @Autowired
    private final AdvertiserService advertiserService;

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

    @PutMapping(path = "advertisers", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<AdvertiserDTO> updateAdvertiserById(@RequestBody AdvertiserRequestDTO advertiserData)
            throws Exception {
        Integer userId = getCurrentUserId();
        return new ResponseEntity<>(advertiserService.updateAdvertiserById(userId, advertiserData), HttpStatus.CREATED);
    }

    @PostMapping("advertisings")
    public ResponseEntity<AdvertisingDTO> createAdvertising(@RequestBody AdvertisingRequestDTO advertisingRequest)
            throws Exception {
        Integer userId = getCurrentUserId();
        advertisingRequest.setAdvertiserId(userId);
        return ResponseEntity.ok(advertiserService.createAdvertising(advertisingRequest));
    }

    @GetMapping("advertisings")
    public ResponseEntity<List<AdvertisingDTO>> getAllAdvertisings() throws Exception {
        Integer userId = getCurrentUserId();
        return ResponseEntity.ok(advertiserService.getAllAdvertisingsByAdvertiser(userId));
    }

    @GetMapping("advertisings/{id}")
    public ResponseEntity<AdvertisingDTO> getAdvertisingsById(@PathVariable Integer id) throws Exception {
        AdvertisingDTO response = advertiserService.getAdvertisingById(id);
        Integer userId = getCurrentUserId();

        if (response.getAdvertiserId() != userId) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("advertisings/{id}")
    public ResponseEntity<AdvertisingDTO> updateAdvertisingsById(@PathVariable Integer id,
            @RequestBody AdvertisingRequestDTO advertisingRequest) throws Exception {
        
        AdvertisingDTO advertising = advertiserService.getAdvertisingById(id);
        Integer userId = getCurrentUserId();
        
        if (advertising.getAdvertiserId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        advertisingRequest.setAdvertiserId(userId);

        AdvertisingDTO response = advertiserService.updateAdvertisingById(id, advertisingRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("advertisings/{id}")
    public ResponseEntity<Integer> deleteAdvertisingsById(@PathVariable Integer id) throws Exception {
        AdvertisingDTO advertising = advertiserService.getAdvertisingById(id);
        Integer userId = getCurrentUserId();
        
        if (advertising.getAdvertiserId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        return ResponseEntity.ok(advertiserService.deleteAdvertisingById(id));
    }
}
