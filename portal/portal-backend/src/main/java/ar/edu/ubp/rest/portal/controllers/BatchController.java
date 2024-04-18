package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.services.AdvertisingService;
import ar.edu.ubp.rest.portal.services.FilmService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/batch/")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class BatchController {

    @Autowired
    private final FilmService filmServices;

    @Autowired
    private final AdvertisingService advertisingService;

    @GetMapping("advertisings")
    public ResponseEntity<String> getAllAdvertisingsFromAdvertisers() throws Exception {
        return new ResponseEntity<String>(advertisingService.getAllAdvertisingsFromAdvertisers(),
                HttpStatus.CREATED);
    }

    @GetMapping("films")
    public ResponseEntity<String> getAllFilmsFromPlatforms() throws Exception {
        return new ResponseEntity<String>(filmServices.getAllFilmsFromPlatforms(),
                HttpStatus.CREATED);
    }

    @PostMapping("countries")
    public ResponseEntity<String> loadAllCountryes(@RequestBody List<CountryDTO> countries) {
        return new ResponseEntity<String>(filmServices.loadAllCountries(countries), HttpStatus.CREATED);
    }

}
