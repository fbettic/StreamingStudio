package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.response.FilmSubscriberResponseDTO;
import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;
import ar.edu.ubp.rest.portal.services.FilmService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/films")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class FilmsController {

    private final FilmService filmServices;

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

    @GetMapping("")
    public ResponseEntity<List<FilmSubscriberResponseDTO>> getAllFilms() throws Exception {
        return ResponseEntity.ok(filmServices.getAllFilms(getCurrentUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmSubscriberResponseDTO> getFilmById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(filmServices.getFilmById(id, getCurrentUserId()));
    }

    @GetMapping("highlighted")
    public ResponseEntity<List<FilmSubscriberResponseDTO>> getHighlightedFilms() throws Exception {
        return ResponseEntity.ok(filmServices.getHighlightedFilms(getCurrentUserId()));
    }

    @GetMapping("new")
    public ResponseEntity<List<FilmSubscriberResponseDTO>> getNewFilms() throws Exception {
        return ResponseEntity.ok(filmServices.getNewFilms(getCurrentUserId()));
    }

    @GetMapping("top")
    public ResponseEntity<List<FilmSubscriberResponseDTO>> getMostViewedFilms() throws Exception {
        return ResponseEntity.ok(filmServices.getMostViewedFilms(getCurrentUserId()));
    }

}
