package ar.edu.ubp.rest.portal.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.response.FilmSubscriberResponseDTO;
import ar.edu.ubp.rest.portal.repositories.FilmRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilmService {
    @Autowired
    private final FilmRepository filmRepository;

    @Autowired
    private BatchService batchService;

    public String loadAllCountries(List<CountryDTO> countries) {
        return filmRepository.loadAllCountries(countries);
    }

    public String getAllFilmsFromPlatforms() throws Exception {
        return batchService.updateFilms();
    }

    public List<FilmSubscriberResponseDTO> getAllFilms(Integer subscriberId, String query, String by) {
        List<FilmSubscriberResponseDTO> allFilms = filmRepository.getAllFilms(subscriberId);

        if (query.equals("")) {
            return allFilms;
        }

        String lowerCaseQuery = query.toLowerCase();

        List<FilmSubscriberResponseDTO> filteredFilms = allFilms.stream()
                .filter(film -> {
                    switch (by) {
                        case "description":
                            return film.getDescription() != null
                                    && film.getDescription().toLowerCase().contains(lowerCaseQuery);
                        case "director":
                            return film.getDirector() != null
                                    && film.getDirector().toLowerCase().contains(lowerCaseQuery);
                        case "year":
                            return film.getYear() != null && film.getYear().toString().contains(lowerCaseQuery);
                        case "actors":
                            return film.getActors() != null && film.getActors().toLowerCase().contains(lowerCaseQuery);
                        case "genres":
                            return film.getGenres() != null && film.getGenres().toLowerCase().contains(lowerCaseQuery);
                        case "title":
                            return film.getTitle() != null && film.getTitle().toLowerCase().contains(lowerCaseQuery);
                        case "all":
                            return (film.getDescription() != null
                                    && film.getDescription().toLowerCase().contains(lowerCaseQuery)) ||
                                    (film.getDirector() != null
                                            && film.getDirector().toLowerCase().contains(lowerCaseQuery))
                                    ||
                                    (film.getYear() != null && film.getYear().toString().contains(lowerCaseQuery)) ||
                                    (film.getActors() != null
                                            && film.getActors().toLowerCase().contains(lowerCaseQuery))
                                    ||
                                    (film.getGenres() != null
                                            && film.getGenres().toLowerCase().contains(lowerCaseQuery))
                                    ||
                                    (film.getTitle() != null && film.getTitle().toLowerCase().contains(lowerCaseQuery));
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());

        return filteredFilms;
    }

    public FilmSubscriberResponseDTO getFilmById(Integer filmId, Integer subscriberId) {
        return filmRepository.getFilmById(filmId, subscriberId);
    }

    public List<FilmSubscriberResponseDTO> getHighlightedFilms(Integer subscriberId) {
        return filmRepository.getHighlightedFilms(subscriberId);
    }

    public List<FilmSubscriberResponseDTO> getNewFilms(Integer subscriberId) {
        return filmRepository.getNewFilms(subscriberId);
    }

    public List<FilmSubscriberResponseDTO> getMostViewedFilms(Integer subscriberId) {
        return filmRepository.getMostViewedFilms(subscriberId);
    }

}
