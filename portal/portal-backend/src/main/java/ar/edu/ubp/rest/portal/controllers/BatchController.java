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
import ar.edu.ubp.rest.portal.dto.response.MessageResponseDTO;
import ar.edu.ubp.rest.portal.services.BatchService;
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
    private final BatchService batchService;

    @GetMapping("advertisings")
    public ResponseEntity<MessageResponseDTO> getAllAdvertisingsFromAdvertisers() throws Exception {
        return new ResponseEntity<MessageResponseDTO>(new MessageResponseDTO(batchService.updateAdvertisings()),
                HttpStatus.CREATED);
    }

    @GetMapping("films")
    public ResponseEntity<MessageResponseDTO> getAllFilmsFromPlatforms() throws Exception {
        return new ResponseEntity<MessageResponseDTO>(new MessageResponseDTO(filmServices.getAllFilmsFromPlatforms()),
                HttpStatus.CREATED);
    }

    @PostMapping("countries")
    public ResponseEntity<MessageResponseDTO> loadAllCountries(@RequestBody List<CountryDTO> countries) {
        return new ResponseEntity<MessageResponseDTO>(new MessageResponseDTO(filmServices.loadAllCountries(countries)),
                HttpStatus.CREATED);
    }

    @GetMapping("reports")
    public ResponseEntity<MessageResponseDTO> sendWeeklyReport() throws Exception {
        return new ResponseEntity<MessageResponseDTO>(new MessageResponseDTO(batchService.sendWeeklyReport()),
                HttpStatus.CREATED);
    }

}
