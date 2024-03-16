package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

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
import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.AuthResponseDTO;
import ar.edu.ubp.rest.portal.services.AdministratorServices;
import ar.edu.ubp.rest.portal.services.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AdministratorController {
    private final AuthService authService;
    private final AdministratorServices administratorServices;

    @GetMapping("gettest")
    public ResponseEntity<String> getTest() {
        String message = "Helloo! :D, este endpoint necesita login y ser admin";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("advertisers")
    public ResponseEntity<List<AdvertiserDTO>> getAdvertisers() {
        return ResponseEntity.ok(administratorServices.getAdvertisers());
    }

    @DeleteMapping(path = "advertisers/{id}")
    public ResponseEntity<Integer> deleteAdvertiserById(@PathVariable Integer id) {
        return new ResponseEntity<>(administratorServices.deleteAdvertiserById(id), HttpStatus.CREATED);
    }

    @PutMapping(path = "advertisers/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<AdvertiserDTO> updateAdvertiserById(@PathVariable Integer id,
            @RequestBody AdvertiserRequestDTO advertiserData) {
        return new ResponseEntity<>(administratorServices.updateAdvertiserById(id, advertiserData), HttpStatus.CREATED);
    }

    @PostMapping("advertisers")
    public ResponseEntity<AuthResponseDTO> createAdvertiser(@RequestBody AdvertiserRequestDTO advertiserData) {
        return ResponseEntity.ok(authService.createAdvertiser(advertiserData));
    }

    @PostMapping("countries")
    public ResponseEntity<String> loadAllCountryes(@RequestBody List<CountryDTO> countries) {
        return new ResponseEntity<String>(administratorServices.loadAllCountryes(countries), HttpStatus.CREATED);
    }

    @GetMapping("films")
    public ResponseEntity<List<FilmDTO>> getFilms() {
        return ResponseEntity.ok(administratorServices.getFilms());
    }
}
