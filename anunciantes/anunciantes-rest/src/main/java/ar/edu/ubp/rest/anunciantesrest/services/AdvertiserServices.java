package ar.edu.ubp.rest.anunciantesrest.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.anunciantesrest.beans.AdvertisingBean;
import ar.edu.ubp.rest.anunciantesrest.beans.AuthTokenRequestBean;
import ar.edu.ubp.rest.anunciantesrest.beans.BannerBean;
import ar.edu.ubp.rest.anunciantesrest.repositories.AdvertisingRepository;
import ar.edu.ubp.rest.anunciantesrest.repositories.BannerRepository;
import ar.edu.ubp.rest.anunciantesrest.repositories.ServiceConnectionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertiserServices {
    private final AdvertisingRepository advertisingRepository;
    private final BannerRepository bannerRepository;
    private final ServiceConnectionRepository serviceConnectionRepository;

    public BannerBean getBannerById(Integer Id, String token) throws Exception {
        Integer serviceId = serviceConnectionRepository.getServiceId(token);
        if (serviceId == null || serviceId == 0) {
            throw new Exception("Invalid token");
        }
        try {
            return bannerRepository.getBannerById(Id);
        } catch (Exception e) {
            throw new Exception("Error when obtaining banner");
        }
    }

    public AdvertisingBean getAdvertisingById(Integer Id, String token) throws Exception {
        Integer serviceId = serviceConnectionRepository.getServiceId(token);
        if (serviceId == null || serviceId == 0) {
            throw new Exception("Invalid token");
        }
        try {
            return advertisingRepository.getAdvertisingById(Id);
        } catch (Exception e) {
            throw new Exception("Error when obtaining ad");
        }
    }

    public List<AdvertisingBean> getAllAdvertisings(String token) throws Exception {

        System.out.println("-------------------------> " + token + "<-------------------------");
        Integer serviceId = serviceConnectionRepository.getServiceId(token);
        if (serviceId == null || serviceId == 0) {
            throw new Exception("Invalid token");
        }

        try {
            return advertisingRepository.getAllAdvertisings(serviceId);
        } catch (Exception e) {
            throw new Exception("Error when obtaining ads");
        }
    }

    public String ping(AuthTokenRequestBean authToken) throws Exception {
        Integer serviceId = serviceConnectionRepository.getServiceId(authToken.getAuthToken());
        if (serviceId == null || serviceId == 0) {
            throw new Exception("Invalid token");
        }
        return "pong";
    }

}
