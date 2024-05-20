package ar.edu.ubp.rest.portal.services;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                        .referenceId(advertising.getAdvertisingId())
                        .text(advertising.getText())
                        .imageUrl(advertising.getImageUrl())
                        .redirectUrl(advertising.getRedirectUrl())
                        .build();

                banners.add(banner);
            });
        });

        return advertisingRepository.updateBatchBanner(banners);

    }

    public String updateFilms() throws Exception {

        List<ServiceResponseMapperBean<FilmResponseBean>> clientFilms = platformApiClientService
                .getAllFilmsFromPlatforms();

        List<FilmDTO> allFilms = new ArrayList<>();
        Set<String> filmCodes = new HashSet<>();

        List<PlatformFilmDTO> platformFilms = new ArrayList<>();

        if (Objects.isNull(clientFilms) || clientFilms.isEmpty()) {
            throw new NoSuchElementException("No films availables");
        }

        clientFilms.forEach((client) -> {

            client.getResponseList().forEach((response) -> {
                try {
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
                } catch (Exception e) {
                    throw e;
                }

            });
        });

        filmRepository.updateBatchFilm(allFilms);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            filmRepository.updateBatchPlatformFilm(objectMapper.writeValueAsString(platformFilms));
            return "Success";
        } catch (Exception e) {
            throw e;
        }

    }

    public String sendWeeklyReport() throws Exception {
        LocalDate fromDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate toDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        Map<Integer, String> platformsResult = platformApiClientService.sendWeeklyReport(fromDate, toDate);
        Map<Integer, String> advertisersResult = advertiserApiClientService.sendWeeklyReport(fromDate, toDate);

        return platformsResult.toString() + advertisersResult.toString();
    }

}
