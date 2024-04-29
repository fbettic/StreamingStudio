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
import ar.edu.ubp.rest.portal.beans.request.BasicPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.SessionPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.UserPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AssociationResponseBean;
import ar.edu.ubp.rest.portal.beans.response.FilmResponseBean;
import ar.edu.ubp.rest.portal.beans.response.SessionResponseBean;
import ar.edu.ubp.rest.portal.models.clients.AbstractPlatformApiClient;

public class PlatformRestApiClient extends AbstractPlatformApiClient {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String ping(BasicPayloadBean payload) {
        return restTemplate.postForObject(url + "/ping", payload, String.class);
    }

    @Override
    public List<FilmResponseBean> getAllFilms(BasicPayloadBean payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<BasicPayloadBean> requestEntity = new HttpEntity<>(payload, headers);

        ResponseEntity<FilmResponseBean[]> responseEntity = restTemplate.exchange(
                url + "/films",
                HttpMethod.POST,
                requestEntity,
                FilmResponseBean[].class);

        return Arrays.asList(responseEntity.getBody());
    }

    @Override
    public AssociationResponseBean createAssociationRequest(AssociationRequestPayloadBean payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<AssociationRequestPayloadBean> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<AssociationResponseBean> responseEntity = restTemplate.exchange(
                url + "/associations",
                HttpMethod.POST,
                requestEntity,
                AssociationResponseBean.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // Handle error
            throw new RuntimeException(
                    "Failed to create association. Status code: " + responseEntity.getStatusCode().toString());
        }
    }

    @Override
    public AssociationResponseBean getAssociationData(AssociationPayloadBean payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<BasicPayloadBean> requestEntity = new HttpEntity<>(new BasicPayloadBean(payload.getAuthToken()),
                headers);
        ResponseEntity<AssociationResponseBean> responseEntity = restTemplate.exchange(
                url + "/associations/" + payload.getAssociationId(),
                HttpMethod.POST,
                requestEntity,
                AssociationResponseBean.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // Handle error
            throw new RuntimeException(
                    "Failed to create association. Status code: " + responseEntity.getStatusCode().toString());
        }
    }

    @Override
    public SessionResponseBean createSession(SessionPayloadBean payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<SessionPayloadBean> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<SessionResponseBean> responseEntity = restTemplate.exchange(
                url + "/sessions",
                HttpMethod.POST,
                requestEntity,
                SessionResponseBean.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // Handle error
            throw new RuntimeException(
                    "Failed to create session. Status code: " + responseEntity.getStatusCode().toString());
        }
    }

    @Override
    public AssociationResponseBean cancelAssociationRequest(UserPayloadBean payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<UserPayloadBean> requestEntity = new HttpEntity<>(payload,
                headers);
        ResponseEntity<AssociationResponseBean> responseEntity = restTemplate.exchange(
                url + "/associations/cancel",
                HttpMethod.POST,
                requestEntity,
                AssociationResponseBean.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // Handle error
            throw new RuntimeException(
                    "Failed to create association. Status code: " + responseEntity.getStatusCode().toString());
        }
    }

}
