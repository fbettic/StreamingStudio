package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.AssociationDTO;
import ar.edu.ubp.rest.portal.dto.AssociationRequestDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.SessionDTO;
import ar.edu.ubp.rest.portal.dto.request.NewAssociationRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SessionRequestDTO;
import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;
import ar.edu.ubp.rest.portal.services.SubscriberServices;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/subscriber/")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class SubscriberController {
    @Autowired
    private final SubscriberServices subscriberServices;

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

    @GetMapping("films")
    public ResponseEntity<List<FilmDTO>> getAllFilms() {
        return ResponseEntity.ok(subscriberServices.getAllFilms());
    }

    @GetMapping("advertisings")
    public ResponseEntity<List<AdvertisingDTO>> getAllAdvertisings() {
        return ResponseEntity.ok(subscriberServices.getAllAdvertisings());
    }

    @PostMapping("associations")
    public ResponseEntity<AssociationRequestDTO> createAssociationRequest(@RequestBody NewAssociationRequestDTO newAssociationRequest)
            throws Exception {
                Integer userId = getCurrentUserId();
                newAssociationRequest.setSubscriberId(userId);
        return ResponseEntity.ok(subscriberServices.createAssociationRequest(newAssociationRequest));
    }

    @GetMapping("associations")
    public ResponseEntity<AssociationDTO> getAssociationData(@RequestParam String uuid) {
        return ResponseEntity.ok(subscriberServices.getAssociationData(uuid));
    }

    @PostMapping("sessions")
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionRequestDTO sessionRequest)
            throws Exception {
        Integer userId = getCurrentUserId();
        sessionRequest.setSubscriberId(userId);
        return ResponseEntity.ok(subscriberServices.createSession(sessionRequest));
    }

}
