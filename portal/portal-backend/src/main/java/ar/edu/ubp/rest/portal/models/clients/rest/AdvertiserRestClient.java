package ar.edu.ubp.rest.portal.models.clients.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ar.edu.ubp.rest.portal.beans.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.AuthTokenRequestBean;
import ar.edu.ubp.rest.portal.beans.BannerResponseBean;
import ar.edu.ubp.rest.portal.models.clients.AbstractAdvertiserClient;

public class AdvertiserRestClient extends AbstractAdvertiserClient{
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String ping(AuthTokenRequestBean authToken) {
        System.out.println("------------->"+url);
        return restTemplate.postForObject(url + "/ping", authToken, String.class);
    }

    @Override
    public BannerResponseBean getBannerById(Integer id, AuthTokenRequestBean authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<AuthTokenRequestBean> requestEntity = new HttpEntity<>(authToken, headers);
         ResponseEntity<BannerResponseBean> responseEntity = restTemplate.exchange(
            url + "/banners/" + id,
            HttpMethod.POST,
            requestEntity,
            BannerResponseBean.class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // Handle error
            throw new RuntimeException("Failed to retrieve banner. Status code: " + responseEntity.getStatusCode().toString());
        }
    }

    @Override
    public List<AdvertisingResponseBean> getAllAdvertisings(AuthTokenRequestBean authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<AuthTokenRequestBean> requestEntity = new HttpEntity<>(authToken, headers);

        ResponseEntity<AdvertisingResponseBean[]> responseEntity = restTemplate.exchange(
            url + "/advertisings",
            HttpMethod.POST,
            requestEntity,
            AdvertisingResponseBean[].class
        );

        return Arrays.asList(responseEntity.getBody());
    }

    
    
}


