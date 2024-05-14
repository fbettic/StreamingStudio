package ar.edu.ubp.rest.portal.models.clients.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ar.edu.ubp.rest.portal.beans.request.AssociationPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.AssociationRequestPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.ServicePayloadBean;
import ar.edu.ubp.rest.portal.beans.request.SessionPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.UserPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyPlatformReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AssociationResponseBean;
import ar.edu.ubp.rest.portal.beans.response.FilmResponseBean;
import ar.edu.ubp.rest.portal.beans.response.BasicResponseBean;
import ar.edu.ubp.rest.portal.beans.response.SessionResponseBean;
import ar.edu.ubp.rest.portal.models.clients.AbstractPlatformApiClient;

public class PlatformRestApiClient extends AbstractPlatformApiClient {
    private static final String PING_URL = "/ping";
    private static final String FILMS_URL = "/films";
    private static final String ASSOCIATIONS_URL = "/associations";
    private static final String SESSIONS_URL = "/sessions";
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
    public String ping(ServicePayloadBean payload) {
        return restTemplate.postForObject(url + PING_URL, payload, String.class);
    }

    @Override
    public List<FilmResponseBean> getAllFilms(ServicePayloadBean payload) {
        HttpEntity<ServicePayloadBean> requestEntity = createHttpEntity(payload);
        ResponseEntity<FilmResponseBean[]> responseEntity = restTemplate.exchange(
                url + FILMS_URL,
                HttpMethod.POST,
                requestEntity,
                FilmResponseBean[].class);

        return Arrays.asList(processResponse(responseEntity));
    }

    @Override
    public AssociationResponseBean createAssociationRequest(AssociationRequestPayloadBean payload) {
        HttpEntity<AssociationRequestPayloadBean> requestEntity = createHttpEntity(payload);
        ResponseEntity<AssociationResponseBean> responseEntity = restTemplate.exchange(
                url + ASSOCIATIONS_URL,
                HttpMethod.POST,
                requestEntity,
                AssociationResponseBean.class);

        return processResponse(responseEntity);
    }

    @Override
    public AssociationResponseBean getAssociationData(AssociationPayloadBean payload) {
        HttpEntity<ServicePayloadBean> requestEntity = createHttpEntity(new ServicePayloadBean(payload.getAuthToken()));
        ResponseEntity<AssociationResponseBean> responseEntity = restTemplate.exchange(
                url + ASSOCIATIONS_URL + "/" + payload.getAssociationId(),
                HttpMethod.POST,
                requestEntity,
                AssociationResponseBean.class);

        return processResponse(responseEntity);
    }

    @Override
    public SessionResponseBean createSession(SessionPayloadBean payload) {
        HttpEntity<SessionPayloadBean> requestEntity = createHttpEntity(payload);
        ResponseEntity<SessionResponseBean> responseEntity = restTemplate.exchange(
                url + SESSIONS_URL,
                HttpMethod.POST,
                requestEntity,
                SessionResponseBean.class);

        return processResponse(responseEntity);
    }

    @Override
    public AssociationResponseBean cancelAssociationRequest(UserPayloadBean payload) {
        HttpEntity<UserPayloadBean> requestEntity = createHttpEntity(payload);
        ResponseEntity<AssociationResponseBean> responseEntity = restTemplate.exchange(
                url + ASSOCIATIONS_URL + "/cancel",
                HttpMethod.POST,
                requestEntity,
                AssociationResponseBean.class);

        return processResponse(responseEntity);
    }

    @Override
    public String sendWeeklyReport(WeeklyPlatformReportPayloadBean payload) {
        HttpEntity<WeeklyPlatformReportPayloadBean> requestEntity = createHttpEntity(payload);
        ResponseEntity<BasicResponseBean> responseEntity = restTemplate.exchange(
                url + REPORT_URL,
                HttpMethod.POST,
                requestEntity,
                BasicResponseBean.class);

        return processResponse(responseEntity).getResponse();
    }

}
