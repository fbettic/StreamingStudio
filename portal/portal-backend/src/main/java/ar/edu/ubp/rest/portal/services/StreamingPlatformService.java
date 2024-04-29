package ar.edu.ubp.rest.portal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.dto.AssociationDTO;
import ar.edu.ubp.rest.portal.dto.StreamingPlatformDTO;
import ar.edu.ubp.rest.portal.dto.request.StreamingPlatformRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.StreamingPlatformSubscriberResponseDTO;
import ar.edu.ubp.rest.portal.repositories.AssociationRepository;
import ar.edu.ubp.rest.portal.repositories.StreamingPlatformRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StreamingPlatformService {
    @Autowired
    private final StreamingPlatformRepository streamingPlatformRepository;
    private final AssociationRepository associationRepository;

    public StreamingPlatformDTO createStreamingPlatform(StreamingPlatformRequestDTO streamingPlatformRequest) {
        return streamingPlatformRepository.createStreamingPlatform(streamingPlatformRequest);
    }

    public List<StreamingPlatformDTO> getAllStreamingPlatforms() {
        return streamingPlatformRepository.getAllStreamingPlatfroms();
    }


    public List<StreamingPlatformSubscriberResponseDTO> getStreamingPlatformSubscriber(Integer susbscriberId) {
        List<StreamingPlatformDTO> platforms = streamingPlatformRepository.getAllStreamingPlatfroms();

        List<AssociationDTO> associations = associationRepository.getAllAssociationsBySubscriber(susbscriberId);

        List<StreamingPlatformSubscriberResponseDTO> result = new ArrayList<StreamingPlatformSubscriberResponseDTO>();

        platforms.forEach((platform) -> {
            AtomicBoolean linked = new AtomicBoolean(false);

            associations.forEach(association -> {
                if (association.getPlatformId() == platform.getPlatformId()) {
                    linked.set(true);
                }
            });

            StreamingPlatformSubscriberResponseDTO element = StreamingPlatformSubscriberResponseDTO.builder()
                    .platformId(platform.getPlatformId()).platformName(platform.getPlatformName()).linked(linked.get())
                    .build();
            result.add(element);
        });

        return result;
    }

    public StreamingPlatformDTO getStreamingPlatformById(Integer platformId) {
        return streamingPlatformRepository.getStreamingPlatformById(platformId);
    }

    public Integer deleteStreamingPlatfromById(Integer id) {
        return streamingPlatformRepository.deleteStreamingPlatfromById(id);
    }

    public StreamingPlatformDTO updateStreamingPlatfromById(Integer id,
            StreamingPlatformRequestDTO streamingPlatformRequest) {
        return streamingPlatformRepository.updateStreamingPlatform(streamingPlatformRequest, id);
    }
}
