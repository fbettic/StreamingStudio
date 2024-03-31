package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.BannerResponseBean;
import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.repositories.AdvertiserRepository;
import ar.edu.ubp.rest.portal.repositories.AdvertisingRepository;
import ar.edu.ubp.rest.portal.repositories.FilmRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministratorService {
    @Autowired
    private final AdvertiserRepository advertiserRepository;
    @Autowired
    private final FilmRepository filmRepository;
    @Autowired
    private final AdvertisingRepository advertisingRepository;

    @Autowired
    private final AdvertiserClientService advertiserClientService;

    public List<AdvertiserDTO> getAllAdvertisers() {
        return advertiserRepository.getAllAdvertisers();
    }

    public Integer deleteAdvertiserById(Integer id) {
        return advertiserRepository.deleteAdvertiserById(id);
    }

    public AdvertiserDTO updateAdvertiserById(Integer id, AdvertiserRequestDTO advertiserData) {
        return advertiserRepository.updateAdvertiserById(advertiserData, id);
    }

    public String loadAllCountryes(List<CountryDTO> countries) {
        int countriesLoaded = filmRepository.loadAllCountries(countries);

        if (countriesLoaded == 0) {
            return "No countries were created.";
        } else {
            return "Successfully loaded " + countriesLoaded + " countries.";
        }
    }

    public List<FilmDTO> getAllFilms() {
        return filmRepository.getAllFilms();
    }

    public List<AdvertisingDTO> getAllAdvertisings () {
        return advertisingRepository.getAllAdvertisings();
    }

    public AdvertisingDTO createAdvertising(AdvertisingRequestDTO advertisingRequest){
        if (advertisingRequest.getBannerId() != null && advertisingRequest.getBannerId() > 0) {
           BannerResponseBean bannerData = advertiserClientService.getBannerById(advertisingRequest.getBannerId(), advertisingRequest.getAdvertiserId());

           advertisingRequest.setBannerText(bannerData.getText());
           advertisingRequest.setImageUrl(bannerData.getImageUrl());
           advertisingRequest.setRedirectUrl(bannerData.getRedirectUrl());
        }

        return advertisingRepository.createAdvertising(advertisingRequest);
    }
}
