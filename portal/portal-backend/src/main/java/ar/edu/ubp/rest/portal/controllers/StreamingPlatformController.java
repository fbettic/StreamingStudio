package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.StreamingPlatformDTO;
import ar.edu.ubp.rest.portal.dto.request.StreamingPlatformRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.StreamingPlatformSubscriberResponseDTO;
import ar.edu.ubp.rest.portal.enums.Role;
import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;
import ar.edu.ubp.rest.portal.services.StreamingPlatformService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/platforms")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class StreamingPlatformController {

    @Autowired
    private final StreamingPlatformService streamingPlatform;

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
    public ResponseEntity<StreamingPlatformDTO> createStreamingPlatform(
            @RequestBody StreamingPlatformRequestDTO streamingPlatformRequest) {

        return ResponseEntity.ok(streamingPlatform.createStreamingPlatform(streamingPlatformRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingPlatformDTO> getStreamingPlatformById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(streamingPlatform.getStreamingPlatformById(id));
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getAllStreamingPlatforms() throws Exception {
        if (getCurrentRole().name().equals("SUBSCRIBER")) {
            return ResponseEntity.ok(streamingPlatform.getStreamingPlatformSubscriber(getCurrentUserId()));
        } else {
            return ResponseEntity.ok(streamingPlatform.getAllStreamingPlatforms());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamingPlatformDTO> updateStreamingPlatform(@PathVariable Integer id,
            @RequestBody StreamingPlatformRequestDTO streamingPlatformRequest) {
        return ResponseEntity
                .ok(streamingPlatform.updateStreamingPlatfromById(id, streamingPlatformRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteStreamingPlatformById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(streamingPlatform.deleteStreamingPlatfromById(id));
    }

}
