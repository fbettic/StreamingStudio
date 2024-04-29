package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.PlatformFilmDTO;
import ar.edu.ubp.rest.portal.dto.response.FilmSubscriberResponseDTO;

public interface IFilmRepository {
    public Integer loadAllCountries(List<CountryDTO> countries);
    public Integer updateBatchFilm(List<FilmDTO> films);
    public Integer dropAllPlatformFilmRelations();
    public Integer updateBatchPlatformFilm(List<PlatformFilmDTO> streamingPlatformFilms);
    public List<FilmDTO> getAllFilms();
    public FilmSubscriberResponseDTO getFilmById(Integer id);
}
