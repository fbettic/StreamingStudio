package ar.edu.ubp.rest.portal.models.clients.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ar.edu.ubp.rest.portal.beans.request.BasicPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyAdvertiserReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BannerResponseBean;
import ar.edu.ubp.rest.portal.beans.response.ReportResponseBean;
import ar.edu.ubp.rest.portal.models.clients.AbstractAdvertiserApiClient;

public class AdvertiserRestApiClient extends AbstractAdvertiserApiClient {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String ping(BasicPayloadBean authToken) {
        System.out.println("------------->" + url);
        return restTemplate.postForObject(url + "/ping", authToken, String.class);
    }

    @Override
    public BannerResponseBean getBannerById(Integer id, BasicPayloadBean authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<BasicPayloadBean> requestEntity = new HttpEntity<>(authToken, headers);
        ResponseEntity<BannerResponseBean> responseEntity = restTemplate.exchange(
                url + "/banners/" + id,
                HttpMethod.POST,
                requestEntity,
                BannerResponseBean.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // Handle error
            throw new RuntimeException(
                    "Failed to retrieve banner. Status code: " + responseEntity.getStatusCode().toString());
        }
    }

    @Override
    public List<AdvertisingResponseBean> getAllAdvertisings(BasicPayloadBean authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<BasicPayloadBean> requestEntity = new HttpEntity<>(authToken, headers);

        ResponseEntity<AdvertisingResponseBean[]> responseEntity = restTemplate.exchange(
                url + "/advertisings",
                HttpMethod.POST,
                requestEntity,
                AdvertisingResponseBean[].class);

        return Arrays.asList(responseEntity.getBody());
    }

    @Override
    public String sendWeeklyReport(WeeklyAdvertiserReportPayloadBean payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<WeeklyAdvertiserReportPayloadBean> requestEntity = new HttpEntity<>(payload,
                headers);
        ResponseEntity<ReportResponseBean> responseEntity = restTemplate.exchange(
                url + "/report",
                HttpMethod.POST,
                requestEntity,
                ReportResponseBean.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody().getResponse();
        } else {
            // Handle error
            throw new RuntimeException(
                    "Failed to create association. Status code: " + responseEntity.getStatusCode().toString());
        }
    }
}
