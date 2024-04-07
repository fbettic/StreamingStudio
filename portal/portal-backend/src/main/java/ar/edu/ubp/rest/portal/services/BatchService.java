package ar.edu.ubp.rest.portal.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.response.FilmResponseBean;
import ar.edu.ubp.rest.portal.beans.response.ServiceResponseMapperBean;
import ar.edu.ubp.rest.portal.dto.BannerDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.PlatformFilmDTO;
import ar.edu.ubp.rest.portal.repositories.AdvertisingRepository;
import ar.edu.ubp.rest.portal.repositories.FilmRepository;

@Service
public class BatchService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private AdvertisingRepository advertisingRepository;

    public void updateAdvertisings(List<ServiceResponseMapperBean<AdvertisingResponseBean>> clientAdvertisings) {

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

        advertisingRepository.updateBatchBanner(banners);

        return;
    }

    public void updateFilms(List<ServiceResponseMapperBean<FilmResponseBean>> clientFilms) {
        List<FilmDTO> allFilms = new ArrayList<>();
        Set<String> filmCodes = new HashSet<>();

        List<PlatformFilmDTO> platformFilms = new ArrayList<>();

        clientFilms.forEach((client) -> {

            client.getResponseList().forEach((response) -> {

                FilmResponseBean film = (FilmResponseBean) response;

                if (!filmCodes.contains(film.getFilmCode())) {
                    FilmDTO newFilm = FilmDTO.builder()
                            .filmCode(film.getFilmCode())
                            .title(film.getTitle())
                            .cover(film.getCover())
                            .description(film.getDescription())
                            .directorName(film.getDirector())
                            .countryCode(film.getCountryCode())
                            .year(film.getYear())
                            .actorNames(film.getActors())
                            .genreNames(film.getGenres())
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

        Integer filmsCreated = filmRepository.updateBatchFilm(allFilms);

        if (filmsCreated < 0) {
            filmRepository.dropAllPlatformFilmRelations();
            filmRepository.updateBatchPlatformFilm(platformFilms);
        }

    }

}
