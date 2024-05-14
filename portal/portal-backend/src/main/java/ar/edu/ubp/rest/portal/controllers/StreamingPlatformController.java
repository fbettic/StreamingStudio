package ar.edu.ubp.rest.portal.controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.StreamingPlatformDTO;
import ar.edu.ubp.rest.portal.dto.request.StreamingPlatformRequestDTO;
import ar.edu.ubp.rest.portal.enums.Role;
import ar.edu.ubp.rest.portal.services.CustomUserDetailsService;
import ar.edu.ubp.rest.portal.services.StreamingPlatformService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/platforms")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class StreamingPlatformController {

    @Autowired
    private final StreamingPlatformService streamingPlatform;

    @Autowired
    private final CustomUserDetailsService userService;

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
        if (userService.getCurrentRole().equals(Role.SUBSCRIBER)) {
            return ResponseEntity.ok(streamingPlatform.getStreamingPlatformSubscriber(userService.getCurrentUserId()));
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
