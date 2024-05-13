package ar.edu.ubp.rest.portal.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.AssociationDTO;
import ar.edu.ubp.rest.portal.dto.AssociationRequestDTO;
import ar.edu.ubp.rest.portal.dto.SessionDTO;
import ar.edu.ubp.rest.portal.dto.request.NewAssociationRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SessionRequestDTO;
import ar.edu.ubp.rest.portal.enums.Role;
import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;
import ar.edu.ubp.rest.portal.services.AssociationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/associations")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AssociationController {

    @Autowired
    private final AssociationService associationService;

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

    @PostMapping("/link")
    public ResponseEntity<AssociationRequestDTO> createAssociationRequest(
            @RequestBody NewAssociationRequestDTO newAssociationRequest)
            throws Exception {
        if (getCurrentRole().name().equals("SUBSCRIBER")) {
            Integer userId = getCurrentUserId();
            newAssociationRequest.setSubscriberId(userId);
        }
        return ResponseEntity.ok(associationService.createAssociationRequest(newAssociationRequest));
    }

    @GetMapping("/unlink/{id}")
    public ResponseEntity<AssociationDTO> cancelAssociationRequest(@PathVariable Integer id)
            throws Exception {
        return ResponseEntity.ok(associationService.cancelAssociationRequest(id, getCurrentUserId()));
    }

    @GetMapping("")
    public ResponseEntity<AssociationDTO> getAssociationData(@RequestParam("uuid") String uuid) {

        return ResponseEntity.ok(associationService.getAssociationData(uuid));
    }

    @GetMapping("/linked")
    public ResponseEntity<List<AssociationDTO>> getAllAssociationsBySubscriber(@RequestParam("uuid") String uuid)
            throws Exception {

        return ResponseEntity.ok(associationService.getAllAssociationsBySubscriber(getCurrentUserId()));
    }

    @PostMapping("/sessions")
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionRequestDTO sessionRequest)
            throws Exception {
        Integer userId = getCurrentUserId();
        sessionRequest.setSubscriberId(userId);
        return ResponseEntity.ok(associationService.createSession(sessionRequest));
    }

    @GetMapping("/sessions/{id}")
    public ResponseEntity<SessionDTO> markSessionAsUsed(@PathVariable Integer id) throws Exception {
        SessionDTO session = associationService.getSessionById(id);

        if ((getCurrentRole().name().equals("SUBSCRIBER")
                && !session.getSubscriberId().equals(getCurrentUserId()))
                || Objects.nonNull(session.getUsedAt())) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(associationService.markSessionAsUsed(id));
    }

}
