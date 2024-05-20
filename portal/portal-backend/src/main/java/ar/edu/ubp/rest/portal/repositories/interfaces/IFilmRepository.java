package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.response.FilmSubscriberResponseDTO;

public interface IFilmRepository {
    public String loadAllCountries(List<CountryDTO> countries);

    public String updateBatchFilm(List<FilmDTO> films);

    public String updateBatchPlatformFilm(String streamingPlatformFilmsJson);

    public List<FilmSubscriberResponseDTO> getAllFilms(Integer subscriberId);

    public FilmSubscriberResponseDTO getFilmById(Integer filmId, Integer subscriberId);

    public List<FilmSubscriberResponseDTO> getHighlightedFilms(Integer subscriberId);

    public List<FilmSubscriberResponseDTO> getNewFilms(Integer subscriberId);

    public List<FilmSubscriberResponseDTO> getMostViewedFilms(Integer subscriberId);

}
