package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.request.ServicePayloadBean;
import ar.edu.ubp.rest.portal.dto.StreamingPlatformDTO;
import ar.edu.ubp.rest.portal.dto.request.StreamingPlatformRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.StreamingPlatformSubscriberResponseDTO;
import ar.edu.ubp.rest.portal.repositories.StreamingPlatformRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StreamingPlatformService {
    @Autowired
    private final PlatformApiClientService platformApiClientService;

    @Autowired
    private final StreamingPlatformRepository streamingPlatformRepository;

    public StreamingPlatformDTO createStreamingPlatform(StreamingPlatformRequestDTO request) throws Exception {
        String result = platformApiClientService.ping(
                request.getPlatformName(),
                request.getServiceType(),
                request.getApiUrl(),
                new ServicePayloadBean(request.getAuthToken()));

        if (!result.equals("pong")) {
            throw new Exception(
                    "Failed to establish a connection with the specified API. Please verify the accuracy of the connection details provided.");
        }

        return streamingPlatformRepository.createStreamingPlatform(request);
    }

    public List<StreamingPlatformDTO> getAllStreamingPlatforms() {
        return streamingPlatformRepository.getAllStreamingPlatfroms();
    }

    public List<StreamingPlatformSubscriberResponseDTO> getStreamingPlatformSubscriber(Integer susbscriberId) {

        return streamingPlatformRepository.getAllStreamingPlatformsBySubscriberId(susbscriberId);
    }

    public StreamingPlatformDTO getStreamingPlatformById(Integer platformId) {
        return streamingPlatformRepository.getStreamingPlatformById(platformId);
    }

    public Integer deleteStreamingPlatfromById(Integer id) {
        return streamingPlatformRepository.deleteStreamingPlatfromById(id);
    }

    public StreamingPlatformDTO updateStreamingPlatfromById(Integer id,
            StreamingPlatformRequestDTO request) throws Exception {
        String result = platformApiClientService.ping(
                request.getPlatformName(),
                request.getServiceType(),
                request.getApiUrl(),
                new ServicePayloadBean(request.getAuthToken()));

        if (!result.equals("pong")) {
            throw new Exception(
                    "Failed to establish a connection with the specified API. Please verify the accuracy of the connection details provided.");
        }
        return streamingPlatformRepository.updateStreamingPlatform(request, id);
    }
}
