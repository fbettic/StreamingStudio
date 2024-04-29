package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.response.FilmSubscriberResponseDTO;
import ar.edu.ubp.rest.portal.repositories.FilmRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilmService {
    @Autowired
    private final FilmRepository filmRepository;

    // Services
    @Autowired
    private BatchService batchService;

    @Autowired
    private final PlatformApiClientService platformApiClientService;

    public String loadAllCountries(List<CountryDTO> countries) {
        int countriesLoaded = filmRepository.loadAllCountries(countries);

        if (countriesLoaded == 0) {
            return "No countries were created.";
        } else {
            return "Successfully loaded " + countriesLoaded + " countries.";
        }
    }

    public String getAllFilmsFromPlatforms() throws Exception {

        try {
            System.out.println("-----------------------> getAllFilmsFromPlatforms()");
            batchService.updateFilms(platformApiClientService.getAllFilmsFromPlatforms());
        } catch (Exception e) {
            throw new Exception(e);
        }
        return "Films have been updated";
    }

    public List<FilmDTO> getAllFilms() {
        return filmRepository.getAllFilms();
    }

    public FilmSubscriberResponseDTO getFilmById(Integer id) {
        return filmRepository.getFilmById(id);
    }

}
