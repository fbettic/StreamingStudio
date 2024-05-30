package ar.edu.ubp.rest.anunciantesrest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.rest.anunciantesrest.beans.AdvertisingBean;
import ar.edu.ubp.rest.anunciantesrest.beans.AuthTokenRequestBean;
import ar.edu.ubp.rest.anunciantesrest.beans.BannerBean;
import ar.edu.ubp.rest.anunciantesrest.beans.BasicResponseBean;
import ar.edu.ubp.rest.anunciantesrest.beans.WeeklyReportBean;
import ar.edu.ubp.rest.anunciantesrest.repositories.AdvertisingRepository;
import ar.edu.ubp.rest.anunciantesrest.repositories.BannerRepository;
import ar.edu.ubp.rest.anunciantesrest.repositories.ReportRepository;
import ar.edu.ubp.rest.anunciantesrest.repositories.ServiceConnectionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertiserServices {
    @Autowired
    private final AdvertisingRepository advertisingRepository;
    @Autowired
    private final BannerRepository bannerRepository;
    @Autowired
    private final ServiceConnectionRepository serviceConnectionRepository;
    @Autowired
    private final ReportRepository reportRepository;

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

    public BasicResponseBean createWeeklyReport(WeeklyReportBean report) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(report);
        } catch (JsonProcessingException e) {
            throw e;
        }

        return reportRepository.createWeekleyReport(jsonString);
    }

    public BasicResponseBean ping(AuthTokenRequestBean authToken) throws Exception {
        Integer serviceId = serviceConnectionRepository.getServiceId(authToken.getAuthToken());
        if (serviceId == null || serviceId == 0) {
            throw new Exception("Invalid token");
        }
        return new BasicResponseBean("pong");
    }

}
