package ar.edu.ubp.rest.anunciantesrest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.anunciantesrest.beans.AdvertisingBean;
import ar.edu.ubp.rest.anunciantesrest.beans.AuthTokenRequestBean;
import ar.edu.ubp.rest.anunciantesrest.beans.BannerBean;
import ar.edu.ubp.rest.anunciantesrest.beans.BasicResponseBean;
import ar.edu.ubp.rest.anunciantesrest.beans.WeeklyReportBean;
import ar.edu.ubp.rest.anunciantesrest.services.AdvertiserServices;

@RestController
@RequestMapping(path = "rest/anunciante", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AnunciantesController {
    @Autowired
    AdvertiserServices advertiserServices;

    @PostMapping(path = "/banners/{bannerId}")
    public ResponseEntity<BannerBean> getBannerById(@PathVariable("bannerId") String bannerId,
            @RequestBody AuthTokenRequestBean authToken) throws NumberFormatException, Exception {
        return new ResponseEntity<>(
                advertiserServices.getBannerById(Integer.valueOf(bannerId), authToken.getAuthToken()),
                HttpStatus.OK);
    }

    @PostMapping(path = "/advertisings/{advertisingId}")
    public ResponseEntity<AdvertisingBean> getAdvertisingById(@PathVariable("advertisingId") String advertisingId,
            @RequestBody AuthTokenRequestBean authToken) throws NumberFormatException, Exception {
        return new ResponseEntity<>(
                advertiserServices.getAdvertisingById(Integer.valueOf(advertisingId), authToken.getAuthToken()),
                HttpStatus.OK);
    }

    @PostMapping(path = "/advertisings")
    public ResponseEntity<List<AdvertisingBean>> getBanner(@RequestBody AuthTokenRequestBean authToken)
            throws NumberFormatException, Exception {
        return new ResponseEntity<>(advertiserServices.getAllAdvertisings(authToken.getAuthToken()), HttpStatus.OK);
    }

    @PostMapping(path = "/report")
    public ResponseEntity<BasicResponseBean> receiveWeeklyReport(@RequestBody WeeklyReportBean report) throws Exception {

        return new ResponseEntity<BasicResponseBean>(advertiserServices.createWeeklyReport(report), HttpStatus.OK);
    }

    @PostMapping(path = "/ping")
    public ResponseEntity<BasicResponseBean> ping(@RequestBody AuthTokenRequestBean authToken) throws Exception {

        return new ResponseEntity<BasicResponseBean>(advertiserServices.ping(authToken), HttpStatus.OK);
    }
}
