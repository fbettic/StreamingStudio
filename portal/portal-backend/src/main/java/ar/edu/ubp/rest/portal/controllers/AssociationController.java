package ar.edu.ubp.rest.portal.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import ar.edu.ubp.rest.portal.dto.request.ObvservationRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SessionRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.MessageResponseDTO;
import ar.edu.ubp.rest.portal.enums.Role;
import ar.edu.ubp.rest.portal.services.AssociationService;
import ar.edu.ubp.rest.portal.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/associations")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AssociationController {

    @Autowired
    private final AssociationService associationService;

    @Autowired
    private final CustomUserDetailsService userService;

    @PostMapping("/link")
    public ResponseEntity<AssociationRequestDTO> createAssociationRequest(
            @RequestBody NewAssociationRequestDTO newAssociationRequest)
            throws Exception {
        if (userService.getCurrentRole().equals(Role.SUBSCRIBER)) {
            Integer userId = userService.getCurrentUserId();
            newAssociationRequest.setSubscriberId(userId);
        }
        return ResponseEntity.ok(associationService.createAssociationRequest(newAssociationRequest));
    }

    @GetMapping("/unlink/{id}")
    public ResponseEntity<AssociationDTO> cancelAssociationRequest(@PathVariable Integer id)
            throws Exception {
        return ResponseEntity.ok(associationService.cancelAssociationRequest(id, userService.getCurrentUserId()));
    }

    @GetMapping("")
    public ResponseEntity<AssociationDTO> getAssociationData(@RequestParam("uuid") String uuid) {

        return ResponseEntity.ok(associationService.getAssociationData(uuid));
    }

    @GetMapping("/linked")
    public ResponseEntity<List<AssociationDTO>> getAllAssociationsBySubscriber(@RequestParam("uuid") String uuid)
            throws Exception {

        return ResponseEntity.ok(associationService.getAllAssociationsBySubscriber(userService.getCurrentUserId()));
    }

    @PostMapping("/sessions")
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionRequestDTO sessionRequest)
            throws Exception {
        sessionRequest.setSubscriberId(userService.getCurrentUserId());
        return ResponseEntity.ok(associationService.createSession(sessionRequest));
    }

    @GetMapping("/sessions/{id}")
    public ResponseEntity<SessionDTO> markSessionAsUsed(@PathVariable Integer id) {
        SessionDTO session = associationService.getSessionById(id);

        if ((userService.getCurrentRole().equals(Role.SUBSCRIBER)
                && !session.getSubscriberId().equals(userService.getCurrentUserId()))
                || Objects.nonNull(session.getUsedAt())) {
            throw new NoSuchElementException("The session has not been found, or has already been used.");
        }

        return ResponseEntity.ok(associationService.markSessionAsUsed(id));
    }

    @PostMapping("/obvservation")
    public ResponseEntity<MessageResponseDTO> createAssociationRequest(
            @RequestBody ObvservationRequestDTO request)
            throws Exception {
        return ResponseEntity.ok(associationService.updateObvservation(request));
    }

}
