package ar.edu.ubp.rest.portal.services;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.request.AssociationPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.AssociationRequestPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.ServicePayloadBean;
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

    @Autowired
    private StreamingPlatformRepository streamingPlatformRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private ReportService reportService;

    private final PlatformApiClientFactory platformClientFactory;

    public PlatformApiClientService() {
        this.platformClientFactory = PlatformApiClientFactory.getInstance();
    }

    private AbstractPlatformApiClient getPlatformClient(StreamingPlatformDTO platform) {
        try {
            return platformClientFactory.buildPlatformClient(
                    platform.getPlatformName(), platform.getServiceType(), platform.getApiUrl(), true);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
            throw new RuntimeException("Error creating PlatformApiClient", e);
        }
    }

    public String ping(String platformName, String serviceType, String url, ServicePayloadBean authToken) {
        try {
            AbstractPlatformApiClient platformClient = platformClientFactory.buildPlatformClient(platformName,
                    serviceType, url, false);
            return platformClient.ping(authToken).getResponse();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
            throw new RuntimeException("Error during ping", e);
        }
    }

    public List<ServiceResponseMapperBean<FilmResponseBean>> getAllFilmsFromPlatforms() throws Exception {
        List<StreamingPlatformDTO> platforms = streamingPlatformRepository.getAllStreamingPlatfroms();
        if (Objects.isNull(platforms) || platforms.isEmpty()) {
            throw new NoSuchElementException("Failed to retrieve data from registered platforms.");
        }

        List<ServiceResponseMapperBean<FilmResponseBean>> filmsByPlatform = new ArrayList<>();

        platforms.forEach((platform) -> {

            if (Objects.nonNull(platform)) {
                try {
                    AbstractPlatformApiClient platformClient = getPlatformClient(platform);
                    ServicePayloadBean authToken = new ServicePayloadBean(platform.getAuthToken());
                    List<FilmResponseBean> films = platformClient.getAllFilms(authToken);

                    if (!films.isEmpty()) {
                        filmsByPlatform.add(new ServiceResponseMapperBean<>(platform.getPlatformId(), films));
                    }
                } catch (Exception e) {

                    System.err
                            .println("Error processing platform " + platform.getPlatformName() + ": " + e.getMessage());
                }
            }
        });

        return filmsByPlatform;
    }

    public AssociationResponseBean createAssociationRequest(NewAssociationRequestDTO newAssociationRequest,
            String redirectUrl) {
        StreamingPlatformDTO platform = streamingPlatformRepository
                .getStreamingPlatformById(newAssociationRequest.getPlatformId());

        if (Objects.isNull(platform)) {
            throw new NoSuchElementException("Failed to retrieve data from registered platforms.");
        }

        try {
            AbstractPlatformApiClient platformClient = getPlatformClient(platform);
            AssociationRequestPayloadBean associationRequest = new AssociationRequestPayloadBean(
                    platform.getAuthToken(), newAssociationRequest.getAssociationType(), redirectUrl);
            return platformClient.createAssociationRequest(associationRequest);
        } catch (Exception e) {
            System.err.println("Error creating association request for platform " + platform.getPlatformName() + ": "
                    + e.getMessage());
            return null;
        }
    }

    public AssociationResponseBean getAssociationData(Integer platformId, Integer transactionId) {
        StreamingPlatformDTO platform = streamingPlatformRepository.getStreamingPlatformById(platformId);
        if (Objects.isNull(platform)) {
            throw new NoSuchElementException("Failed to retrieve data from registered platforms.");
        }

        try {
            AbstractPlatformApiClient platformClient = getPlatformClient(platform);
            AssociationPayloadBean userTokenRequest = new AssociationPayloadBean(platform.getAuthToken(),
                    transactionId);
            return platformClient.getAssociationData(userTokenRequest);
        } catch (Exception e) {
            System.err.println("Error getting association data for platform " + platform.getPlatformName() + ": "
                    + e.getMessage());
            return null;
        }
    }

    public AssociationResponseBean cancelAssociationResponse(Integer platformId, String userToken) {
        StreamingPlatformDTO platform = streamingPlatformRepository.getStreamingPlatformById(platformId);
        if (Objects.isNull(platform))
            return null;

        try {
            AbstractPlatformApiClient platformClient = getPlatformClient(platform);
            UserPayloadBean credentials = new UserPayloadBean(platform.getAuthToken(), userToken);
            return platformClient.cancelAssociationRequest(credentials);
        } catch (Exception e) {
            System.err.println("Error canceling association response for platform " + platform.getPlatformName() + ": "
                    + e.getMessage());
            return null;
        }
    }

    public SessionResponseBean createSession(SessionRequestDTO newSessionRequest) {
        StreamingPlatformDTO platform = streamingPlatformRepository
                .getStreamingPlatformById(newSessionRequest.getPlatformId());
        AssociationDTO association = associationRepository.getAssociationToken(newSessionRequest.getPlatformId(),
                newSessionRequest.getSubscriberId());

        if (Objects.isNull(platform))
            return null;

        try {
            AbstractPlatformApiClient platformClient = getPlatformClient(platform);
            SessionPayloadBean sessionRequest = new SessionPayloadBean(platform.getAuthToken(),
                    newSessionRequest.getFilmCode(), association.getUserToken());
            return platformClient.createSession(sessionRequest);
        } catch (Exception e) {
            System.err.println(
                    "Error creating session for platform " + platform.getPlatformName() + ": " + e.getMessage());
            return null;
        }
    }

    public Map<Integer, String> sendWeeklyReport(LocalDate fromDate, LocalDate toDate) throws Exception {
        List<StreamingPlatformDTO> platforms = streamingPlatformRepository.getAllStreamingPlatfroms();
        if (Objects.isNull(platforms) || platforms.isEmpty()) {
            throw new RuntimeException("Failed to retrieve data from registered platforms.");
        }

        Map<Integer, String> response = new HashMap<>();
        platforms.forEach(platform -> {
            if (Objects.nonNull(platform)) {
                try {
                    AbstractPlatformApiClient platformClient = getPlatformClient(platform);
                    WeeklyPlatformReportPayloadBean report = reportService.createWeeklyPlatformReport(platform.getPlatformId(), platform.getAuthToken(), fromDate, toDate);
                    String result = platformClient.sendWeeklyReport(report).getResponse();
                    response.put(platform.getPlatformId(), result);
                } catch (Exception e) {
                    System.err.println("Error sending weekly report for platform " + platform.getPlatformName() + ": " + e.getMessage());
                    response.put(platform.getPlatformId(), "error");
                }
            }
        });

        return response;

    }
}
