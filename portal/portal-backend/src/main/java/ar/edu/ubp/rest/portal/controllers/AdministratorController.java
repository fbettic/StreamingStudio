package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.StreamingPlatformDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.StreamingPlatformRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.AuthResponseDTO;
import ar.edu.ubp.rest.portal.services.AdministratorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AdministratorController {

    @Autowired
    private final AdministratorService administratorService;

    // Advertiser management endpoints
    @PostMapping("test/advertisers/ping")
    public ResponseEntity<String> pingAdvertiser(@RequestBody AdvertiserRequestDTO advertiser) throws Exception {
        return new ResponseEntity<String>(administratorService.pingAdvertiser(advertiser), HttpStatus.OK);
    }

    @PostMapping("advertisers")
    public ResponseEntity<AuthResponseDTO> createAdvertiser(@RequestBody AdvertiserRequestDTO advertiserData)
            throws Exception {
        return ResponseEntity.ok(administratorService.createAdvertiser(advertiserData));
    }

    @GetMapping("advertisers")
    public ResponseEntity<List<AdvertiserDTO>> getAllAdvertisers() {
        return ResponseEntity.ok(administratorService.getAllAdvertisers());
    }

    @PutMapping(path = "advertisers/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<AdvertiserDTO> updateAdvertiserById(@PathVariable Integer id,
            @RequestBody AdvertiserRequestDTO advertiserData) {
        return new ResponseEntity<>(administratorService.updateAdvertiserById(id, advertiserData), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "advertisers/{id}")
    public ResponseEntity<Integer> deleteAdvertiserById(@PathVariable Integer id) {
        return new ResponseEntity<>(administratorService.deleteAdvertiserById(id), HttpStatus.CREATED);
    }

    // Advertising management endpoints
    @PostMapping("advertisings")
    public ResponseEntity<AdvertisingDTO> createAdvertising(@RequestBody AdvertisingRequestDTO advertisingRequest) {
        System.out.println("----------------> " + advertisingRequest.toString());
        return ResponseEntity.ok(administratorService.createAdvertising(advertisingRequest));
    }

    @GetMapping("advertisings")
    public ResponseEntity<List<AdvertisingDTO>> getAllAdvertisings() {
        return ResponseEntity.ok(administratorService.getAllAdvertisings());
    }

    @GetMapping("batch/advertisings")
    public ResponseEntity<String> getAllAdvertisingsFromAdvertisers() throws Exception {
        return new ResponseEntity<String>(administratorService.getAllAdvertisingsFromAdvertisers(),
                HttpStatus.CREATED);
    }

    // Platform management endpoints
    @PostMapping("platforms")
    public ResponseEntity<StreamingPlatformDTO> createStreamingPlatform(
            @RequestBody StreamingPlatformRequestDTO streamingPlatformRequest) {

        return ResponseEntity.ok(administratorService.createStreamingPlatform(streamingPlatformRequest));
    }

    @GetMapping("platforms/{id}")
    public ResponseEntity<StreamingPlatformDTO> getStreamingPlatformById(
            @PathVariable Integer platformId) {
        return ResponseEntity.ok(administratorService.getStreamingPlatformById(platformId));
    }

    @GetMapping("platforms")
    public ResponseEntity<List<StreamingPlatformDTO>> getAllStreamingPlatforms() {
        return ResponseEntity.ok(administratorService.getAllStreamingPlatforms());
    }

    @PutMapping("platforms/{id}")
    public ResponseEntity<StreamingPlatformDTO> updateStreamingPlatform(@PathVariable Integer platformId,
            @RequestBody StreamingPlatformRequestDTO streamingPlatformRequest) {
        return ResponseEntity
                .ok(administratorService.updateStreamingPlatfromById(platformId, streamingPlatformRequest));
    }

    @DeleteMapping("platforms/{id}")
    public ResponseEntity<Integer> deleteStreamingPlatformById(
            @PathVariable Integer platformId) {
        return ResponseEntity.ok(administratorService.deleteAdvertiserById(platformId));
    }

    // Film management endpoints
    @PostMapping("test/countries")
    public ResponseEntity<String> loadAllCountryes(@RequestBody List<CountryDTO> countries) {
        return new ResponseEntity<String>(administratorService.loadAllCountries(countries), HttpStatus.CREATED);
    }

    @GetMapping("test/films")
    public ResponseEntity<List<FilmDTO>> getAllFilms() {
        return ResponseEntity.ok(administratorService.getAllFilms());
    }

    @GetMapping("batch/films")
    public ResponseEntity<String> getAllFilmsFromPlatforms() throws Exception {
        return new ResponseEntity<String>(administratorService.getAllFilmsFromPlatforms(),
                HttpStatus.CREATED);
    }

}
