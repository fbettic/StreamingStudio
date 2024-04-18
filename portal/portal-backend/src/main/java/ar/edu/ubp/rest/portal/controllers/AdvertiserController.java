package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ar.edu.ubp.rest.portal.dto.response.AuthResponseDTO;
import ar.edu.ubp.rest.portal.enums.Role;
import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;
import ar.edu.ubp.rest.portal.services.AdvertiserService;
import ar.edu.ubp.rest.portal.services.AdvertisingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/advertisers")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AdvertiserController {
    @Autowired
    private final AdvertiserService advertiserService;
    @Autowired
    private final AdvertisingService advertisingService;

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

    @PostMapping("/ping")
    public ResponseEntity<String> pingAdvertiser(@RequestBody AdvertiserRequestDTO advertiser) throws Exception {
        return new ResponseEntity<String>(advertiserService.pingAdvertiser(advertiser), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<AuthResponseDTO> createAdvertiser(@RequestBody AdvertiserRequestDTO advertiserData)
            throws Exception {
        return ResponseEntity.ok(advertiserService.createAdvertiser(advertiserData));
    }

    @PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<AdvertiserDTO> updateAdvertiserById(@PathVariable Integer id,
            @RequestBody AdvertiserRequestDTO advertiserData)
            throws Exception {

        if (getCurrentRole().name() != "ADMINISTRATOR" && id != getCurrentUserId()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(advertiserService.updateAdvertiserById(id, advertiserData), HttpStatus.CREATED);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdvertiserDTO>> getAllAdvertisers() {
        return ResponseEntity.ok(advertiserService.getAllAdvertisers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteAdvertiserById(@PathVariable Integer id) {
        return new ResponseEntity<>(advertiserService.deleteAdvertiserById(id), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/advertisings/")
    public ResponseEntity<List<AdvertisingDTO>> getAllAdvertisingsByAdvertiser(@PathVariable Integer id) {
        return ResponseEntity.ok(advertisingService.getAllAdvertisingsByAdvertiser(id));
    }

}
