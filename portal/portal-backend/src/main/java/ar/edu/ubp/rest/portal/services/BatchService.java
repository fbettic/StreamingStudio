package ar.edu.ubp.rest.portal.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.response.FilmResponseBean;
import ar.edu.ubp.rest.portal.beans.response.ServiceResponseMapperBean;
import ar.edu.ubp.rest.portal.dto.BannerDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.PlatformFilmDTO;
import ar.edu.ubp.rest.portal.repositories.AdvertisingRepository;
import ar.edu.ubp.rest.portal.repositories.FilmRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatchService {

    @Autowired
    private final FilmRepository filmRepository;

    @Autowired
    private final AdvertisingRepository advertisingRepository;

    @Autowired
    private final PlatformApiClientService platformApiClientService;

    @Autowired
    private final AdvertiserApiClientService advertiserApiClientService;

    public String updateAdvertisings() throws Exception {

        List<ServiceResponseMapperBean<AdvertisingResponseBean>> clientAdvertisings = advertiserApiClientService
                .getAllAdvertisingsFromAdvertisers();

        List<BannerDTO> banners = new ArrayList<>();

        clientAdvertisings.forEach((client) -> {
            client.getResponseList().forEach((response) -> {

                AdvertisingResponseBean advertising = (AdvertisingResponseBean) response;

                BannerDTO banner = BannerDTO.builder()
                        .advertiserId(client.getId())
                        .bannerId(advertising.getBannerId())
                        .text(advertising.getText())
                        .imageUrl(advertising.getImageUrl())
                        .redirectUrl(advertising.getRedirectUrl())
                        .build();

                banners.add(banner);
            });
        });

        return advertisingRepository.updateBatchBanner(banners);

    }

    public void updateFilms(List<ServiceResponseMapperBean<FilmResponseBean>> clientFilms) throws Exception {
        List<FilmDTO> allFilms = new ArrayList<>();
        Set<String> filmCodes = new HashSet<>();

        List<PlatformFilmDTO> platformFilms = new ArrayList<>();

        if (Objects.isNull(clientFilms) || clientFilms.size() == 0) {
            throw new NoSuchElementException("No films availables");
        }

        clientFilms.forEach((client) -> {

            client.getResponseList().forEach((response) -> {

                FilmResponseBean film = (FilmResponseBean) response;

                if (!filmCodes.contains(film.getFilmCode())) {
                    FilmDTO newFilm = FilmDTO.builder()
                            .filmCode(film.getFilmCode())
                            .title(film.getTitle())
                            .cover(film.getCover())
                            .description(film.getDescription())
                            .director(film.getDirector())
                            .countryCode(film.getCountryCode())
                            .year(film.getYear())
                            .actors(film.getActors())
                            .genres(film.getGenres())
                            .build();

                    allFilms.add(newFilm);
                    filmCodes.add(film.getFilmCode());
                }

                PlatformFilmDTO platformFilm = PlatformFilmDTO.builder()
                        .filmCode(film.getFilmCode())
                        .platformId(client.getId())
                        .highlighted(film.getHighlighted())
                        .newContent(film.getNewContent())
                        .build();

                platformFilms.add(platformFilm);
            });
        });

        int filmsCreated = filmRepository.updateBatchFilm(allFilms);

        if (filmsCreated != 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = null;
            try {
                jsonString = objectMapper.writeValueAsString(platformFilms);
            } catch (JsonProcessingException e) {
                throw e;
            }
            filmRepository.updateBatchPlatformFilm(jsonString);
        }

    }

    public String sendWeeklyReport() throws Exception {
        Map<Integer, String> platformsResult = platformApiClientService.sendWeeklyReport();
        Map<Integer, String> advertisersResult = advertiserApiClientService.sendWeeklyReport();

        return platformsResult.toString() + advertisersResult.toString();
    }

}
