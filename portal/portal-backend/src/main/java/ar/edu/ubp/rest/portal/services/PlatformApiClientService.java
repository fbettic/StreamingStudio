package ar.edu.ubp.rest.portal.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.request.AssociationPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.AssociationRequestPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.BasicPayloadBean;
import ar.edu.ubp.rest.portal.beans.request.SessionPayloadBean;
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
        System.out.println("---------------->asdasdasd");
        List<StreamingPlatformDTO> platforms = streamingPlatformRepository.getAllStreamingPlatfroms();

        List<ServiceResponseMapperBean<FilmResponseBean>> filmsByPlatform = new ArrayList<>();

        if (Objects.isNull(platforms) || platforms.size() == 0)
            throw new Exception("Failed to retrieve data from registered platforms.");

        System.out.println("--------->" + platforms.toString());

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

        AssociationRequestPayloadBean associationRequest = new AssociationRequestPayloadBean(
                newAssociationRequest.getAssociationType(), platform.getAuthToken(), redirectUrl);

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
            System.out.println(e);
            e.printStackTrace();
        }

        if (Objects.isNull(platformClient))
            return null;

        AssociationPayloadBean userTokenRequest = new AssociationPayloadBean(platform.getAuthToken(),
                transactionId);

        AssociationResponseBean associationResponse = platformClient.getAssociationData(userTokenRequest);

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

}
