package ar.edu.ubp.rest.portal.models.clients.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ar.edu.ubp.rest.portal.beans.request.AdvertisingPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.ServicePayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyAdvertiserReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BasicResponseBean;
import ar.edu.ubp.rest.portal.models.clients.AbstractAdvertiserApiClient;

public class AdvertiserRestApiClient extends AbstractAdvertiserApiClient {
    private static final String PING_URL = "/ping";
    private static final String ADVERTISINGS_URL = "/advertisings";
    private static final String REPORT_URL = "/report";

    private final RestTemplate restTemplate = new RestTemplate();

    private <T> HttpEntity<T> createHttpEntity(T payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(payload, headers);
    }

    private <T> T processResponse(ResponseEntity<T> responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Request failed. Status code: " + responseEntity.getStatusCode());
        }
    }

    @Override
    public BasicResponseBean ping(ServicePayloadBean payload) {
        HttpEntity<ServicePayloadBean> requestEntity = createHttpEntity(payload);
        ResponseEntity<BasicResponseBean> responseEntity = restTemplate.exchange(
                url + PING_URL,
                HttpMethod.POST,
                requestEntity,
                BasicResponseBean.class);

        return processResponse(responseEntity);
    }

    @Override
    public AdvertisingResponseBean getAdvertisingById(AdvertisingPayloadBean payload) {
        HttpEntity<ServicePayloadBean> requestEntity = createHttpEntity(payload.getServicePayload());
        ResponseEntity<AdvertisingResponseBean> responseEntity = restTemplate.exchange(
                url + ADVERTISINGS_URL + "/" + payload.getAdvertisingId(),
                HttpMethod.POST,
                requestEntity,
                AdvertisingResponseBean.class);

        return processResponse(responseEntity);
    }

    @Override
    public List<AdvertisingResponseBean> getAllAdvertisings(ServicePayloadBean authToken) {
        HttpEntity<ServicePayloadBean> requestEntity = createHttpEntity(authToken);
        ResponseEntity<AdvertisingResponseBean[]> responseEntity = restTemplate.exchange(
                url + ADVERTISINGS_URL,
                HttpMethod.POST,
                requestEntity,
                AdvertisingResponseBean[].class);

        return Arrays.asList(processResponse(responseEntity));
    }

    @Override
    public BasicResponseBean sendWeeklyReport(WeeklyAdvertiserReportPayloadBean payload) {
        HttpEntity<WeeklyAdvertiserReportPayloadBean> requestEntity = createHttpEntity(payload);
        ResponseEntity<BasicResponseBean> responseEntity = restTemplate.exchange(
                url + REPORT_URL,
                HttpMethod.POST,
                requestEntity,
                BasicResponseBean.class);

        return processResponse(responseEntity);
    }
}
