package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.response.FilmSubscriberResponseDTO;
import ar.edu.ubp.rest.portal.services.CustomUserDetailsService;
import ar.edu.ubp.rest.portal.services.FilmService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/films")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class FilmsController {

    @Autowired
    private final FilmService filmServices;

    @Autowired
    private final CustomUserDetailsService userService;

    @GetMapping("")
    public ResponseEntity<List<FilmSubscriberResponseDTO>> getAllFilms() throws Exception {
        return ResponseEntity.ok(filmServices.getAllFilms(userService.getCurrentUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmSubscriberResponseDTO> getFilmById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(filmServices.getFilmById(id, userService.getCurrentUserId()));
    }

    @GetMapping("highlighted")
    public ResponseEntity<List<FilmSubscriberResponseDTO>> getHighlightedFilms() throws Exception {
        return ResponseEntity.ok(filmServices.getHighlightedFilms(userService.getCurrentUserId()));
    }

    @GetMapping("new")
    public ResponseEntity<List<FilmSubscriberResponseDTO>> getNewFilms() throws Exception {
        return ResponseEntity.ok(filmServices.getNewFilms(userService.getCurrentUserId()));
    }

    @GetMapping("top")
    public ResponseEntity<List<FilmSubscriberResponseDTO>> getMostViewedFilms() throws Exception {
        return ResponseEntity.ok(filmServices.getMostViewedFilms(userService.getCurrentUserId()));
    }

}
