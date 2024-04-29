package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.response.FilmSubscriberResponseDTO;
import ar.edu.ubp.rest.portal.services.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/films")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class FilmsController {

    private final FilmService filmServices;

    @GetMapping("")
    public ResponseEntity<List<FilmDTO>> getAllFilms() {
        return ResponseEntity.ok(filmServices.getAllFilms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmSubscriberResponseDTO> getFilmById(@PathVariable Integer id) {
        return ResponseEntity.ok(filmServices.getFilmById(id));
    }


}
