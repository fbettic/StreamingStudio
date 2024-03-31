package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;

public interface IFilmRepository {
    public Integer loadAllCountries(List<CountryDTO> countries);
    public List<FilmDTO> getAllFilms();
}
