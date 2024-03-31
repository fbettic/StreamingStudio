package ar.edu.ubp.rest.portal.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.ClientAdvertisingsBean;
import ar.edu.ubp.rest.portal.dto.BannerUpdateDTO;
import ar.edu.ubp.rest.portal.repositories.AdvertisingRepository;

@Service
public class BatchService {

    // @Autowired
    // private FilmRepository filmRepository;

    @Autowired
    private AdvertisingRepository advertisingRepository;

    public void updateAdvertisings(List<ClientAdvertisingsBean> clientAdvertisings) {

        List<BannerUpdateDTO> banners = new ArrayList<>();

        clientAdvertisings.forEach((client) -> {
            client.getAdvertisings().forEach((advertising) -> {

                BannerUpdateDTO banner = BannerUpdateDTO.builder()
                        .advertiserId(client.getAdvertisingId())
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

}
