package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.repositories.AdvertiserRepository;
import ar.edu.ubp.rest.portal.repositories.FilmRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministratorServices {
    private final AdvertiserRepository advertiserRepository;
    private final FilmRepository filmRepository;

    public List<AdvertiserDTO> getAdvertisers() {
        return advertiserRepository.findAdvertisers();
    }

    public Integer deleteAdvertiserById(Integer id) {
        return advertiserRepository.deleteAdvertiserById(id);
    }

    public AdvertiserDTO updateAdvertiserById(Integer id, AdvertiserRequestDTO advertiserData) {
        return advertiserRepository.updateAdvertiserById(advertiserData, id);
    }

    public String loadAllCountryes(List<CountryDTO> countries) {
        return filmRepository.loadAllCountries(countries);
    }

    public List<FilmDTO> getFilms() {
        return filmRepository.findFilms();
    }
}


