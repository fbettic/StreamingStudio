package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.StreamingPlatformDTO;
import ar.edu.ubp.rest.portal.dto.request.StreamingPlatformRequestDTO;
import ar.edu.ubp.rest.portal.services.StreamingPlatformService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/platforms")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class StreamingPlatformController {

    @Autowired
    private final StreamingPlatformService streamingPlatform;

    @PostMapping("")
    public ResponseEntity<StreamingPlatformDTO> createStreamingPlatform(
            @RequestBody StreamingPlatformRequestDTO streamingPlatformRequest) {

        return ResponseEntity.ok(streamingPlatform.createStreamingPlatform(streamingPlatformRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingPlatformDTO> getStreamingPlatformById(
            @PathVariable Integer platformId) {
        return ResponseEntity.ok(streamingPlatform.getStreamingPlatformById(platformId));
    }

    @GetMapping("")
    public ResponseEntity<List<StreamingPlatformDTO>> getAllStreamingPlatforms() {
        return ResponseEntity.ok(streamingPlatform.getAllStreamingPlatforms());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamingPlatformDTO> updateStreamingPlatform(@PathVariable Integer platformId,
            @RequestBody StreamingPlatformRequestDTO streamingPlatformRequest) {
        return ResponseEntity
                .ok(streamingPlatform.updateStreamingPlatfromById(platformId, streamingPlatformRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteStreamingPlatformById(
            @PathVariable Integer platformId) {
        return ResponseEntity.ok(streamingPlatform.deleteStreamingPlatfromById(platformId));
    }

}
