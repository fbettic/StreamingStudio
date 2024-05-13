package ar.edu.ubp.rest.portal.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.rest.portal.beans.request.AssociationPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.AssociationRequestPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.BasicPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.SessionPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.UserPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.WeeklyPlatformReportPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.AssociationResponseBean;
import ar.edu.ubp.rest.portal.beans.response.FilmResponseBean;
import ar.edu.ubp.rest.portal.beans.response.ServiceResponseMapperBean;
import ar.edu.ubp.rest.portal.beans.response.SessionResponseBean;
import ar.edu.ubp.rest.portal.dto.AssociationDTO;
import ar.edu.ubp.rest.portal.dto.StreamingPlatformDTO;
import ar.edu.ubp.rest.portal.dto.request.NewAssociationRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SessionRequestDTO;
import ar.edu.ubp.rest.portal.models.clients.AbstractPlatformApiClient;
import ar.edu.ubp.rest.portal.models.clients.PlatformApiClientFactory;
import ar.edu.ubp.rest.portal.repositories.AssociationRepository;
import ar.edu.ubp.rest.portal.repositories.StreamingPlatformRepository;

@Service
public class PlatformApiClientService {
    private PlatformApiClientFactory platformClientFactory;

    @Autowired
    private StreamingPlatformRepository streamingPlatformRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private ReportService reportService;

    public PlatformApiClientService() {
        this.platformClientFactory = PlatformApiClientFactory.getInstance();
    }

    public String ping(String platformName, String serviceType, String url, BasicPayloadBean authToken)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        AbstractPlatformApiClient platformClient = platformClientFactory.buildPlatformClient(platformName,
                serviceType, url, false);
        return platformClient.ping(authToken);
    }

    public List<ServiceResponseMapperBean<FilmResponseBean>> getAllFilmsFromPlatforms() throws Exception {
        List<StreamingPlatformDTO> platforms = streamingPlatformRepository.getAllStreamingPlatfroms();

        List<ServiceResponseMapperBean<FilmResponseBean>> filmsByPlatform = new ArrayList<>();

        if (Objects.isNull(platforms) || platforms.size() == 0)
            throw new Exception("Failed to retrieve data from registered platforms.");

        platforms.forEach((platform) -> {

            if (Objects.isNull(platform))
                return;

            AbstractPlatformApiClient platformClient = null;

            try {
                platformClient = platformClientFactory.buildPlatformClient(platform.getPlatformName(),
                        platform.getServiceType(), platform.getApiUrl(), true);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                    | IllegalAccessException e) {
                System.out.println(e);
                e.printStackTrace();
            }

            if (Objects.isNull(platformClient))
                return;

            BasicPayloadBean authToken = new BasicPayloadBean(platform.getAuthToken());

            List<FilmResponseBean> films = platformClient.getAllFilms(authToken);

            System.out.println("--------------->" + platform.getPlatformId());
            System.out.println("--------------->" + films.toString());

            if (!films.isEmpty()) {
                filmsByPlatform.add(new ServiceResponseMapperBean<FilmResponseBean>(platform.getPlatformId(), films));
            }
        });

        return filmsByPlatform;
    }

    public AssociationResponseBean createAssociationRequest(NewAssociationRequestDTO newAssociationRequest,
            String redirectUrl) {
        StreamingPlatformDTO platform = streamingPlatformRepository
                .getStreamingPlatformById(newAssociationRequest.getPlatformId());

        if (Objects.isNull(platform))
            return null;

        AbstractPlatformApiClient platformClient = null;
        try {
            platformClient = platformClientFactory.buildPlatformClient(
                    platform.getPlatformName(), platform.getServiceType(), platform.getApiUrl(), true);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        if (Objects.isNull(platformClient))
            return null;

        AssociationRequestPayloadBean associationRequest = new AssociationRequestPayloadBean(platform.getAuthToken(),
                newAssociationRequest.getAssociationType(), redirectUrl);

        AssociationResponseBean associationResponse = platformClient.createAssociationRequest(associationRequest);

        return associationResponse;
    }

    public AssociationResponseBean getAssociationData(Integer platformId, Integer transactionId) {
        StreamingPlatformDTO platform = streamingPlatformRepository
                .getStreamingPlatformById(platformId);

        if (Objects.isNull(platform))
            return null;

        AbstractPlatformApiClient platformClient = null;
        try {
            platformClient = platformClientFactory.buildPlatformClient(
                    platform.getPlatformName(), platform.getServiceType(), platform.getApiUrl(), true);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {

            e.printStackTrace();
        }

        if (Objects.isNull(platformClient))
            return null;

        AssociationPayloadBean userTokenRequest = new AssociationPayloadBean(platform.getAuthToken(),
                transactionId);

        AssociationResponseBean associationResponse = platformClient.getAssociationData(userTokenRequest);

        return associationResponse;
    }

    public AssociationResponseBean cancelAssociationResponse(Integer platformId, String userToken) {
        StreamingPlatformDTO platform = streamingPlatformRepository
                .getStreamingPlatformById(platformId);

        if (Objects.isNull(platform))
            return null;

        AbstractPlatformApiClient platformClient = null;
        try {
            platformClient = platformClientFactory.buildPlatformClient(
                    platform.getPlatformName(), platform.getServiceType(), platform.getApiUrl(), true);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {

            e.printStackTrace();
        }

        if (Objects.isNull(platformClient))
            return null;

        UserPayloadBean credentials = new UserPayloadBean(platform.getAuthToken(), userToken);

        System.out.println("---->" + credentials.toString());

        AssociationResponseBean associationResponse = platformClient.cancelAssociationRequest(credentials);

        return associationResponse;
    }

    public SessionResponseBean createSession(SessionRequestDTO newSessionRequest) {
        StreamingPlatformDTO platform = streamingPlatformRepository
                .getStreamingPlatformById(newSessionRequest.getPlatformId());
        AssociationDTO association = associationRepository.getAssociationToken(newSessionRequest.getPlatformId(),
                newSessionRequest.getSubscriberId());

        if (Objects.isNull(platform))
            return null;

        AbstractPlatformApiClient platformClient = null;
        try {
            platformClient = platformClientFactory.buildPlatformClient(
                    platform.getPlatformName(), platform.getServiceType(), platform.getApiUrl(), true);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        if (Objects.isNull(platformClient))
            return null;

        SessionPayloadBean sessionRequest = new SessionPayloadBean(platform.getAuthToken(),
                newSessionRequest.getFilmCode(), association.getUserToken());

        SessionResponseBean sessionResponse = platformClient.createSession(sessionRequest);

        return sessionResponse;
    }

    public Map<Integer, String> sendWeeklyReport() throws Exception {
        List<StreamingPlatformDTO> platforms = streamingPlatformRepository.getAllStreamingPlatfroms();

        Map<Integer, String> response = new HashMap<Integer, String>();

        if (Objects.isNull(platforms) || platforms.size() == 0)
            throw new Exception("Failed to retrieve data from registered platforms.");

        for (StreamingPlatformDTO platform : platforms) {
            String result = "error";

            if (Objects.isNull(platform)) {
                continue;
            }

            AbstractPlatformApiClient platformClient = null;

            try {
                platformClient = platformClientFactory.buildPlatformClient(platform.getPlatformName(),
                        platform.getServiceType(), platform.getApiUrl(), true);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                    | IllegalAccessException e) {
                System.out.println(e);
                e.printStackTrace();
            }

            if (Objects.isNull(platformClient)) {
                response.put(platform.getPlatformId(), result);
                continue;
            }

            WeeklyPlatformReportPayloadBean report = reportService.createWeeklyPlatformReport(platform.getPlatformId(),
                    platform.getAuthToken());

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = null;
            try {
                jsonString = objectMapper.writeValueAsString(report);
            } catch (JsonProcessingException e) {
                throw e;
            }

            System.out.println(jsonString);
            try {
                result = platformClient.sendWeeklyReport(report);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (Objects.nonNull(result) && result.equals("Success")) {
                response.put(platform.getPlatformId(), result);
            }
        }
        ;

        return response;
    }
}
