package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.dto.StreamingPlatformDTO;
import ar.edu.ubp.rest.portal.dto.request.StreamingPlatformRequestDTO;
import ar.edu.ubp.rest.portal.repositories.StreamingPlatformRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StreamingPlatformService {
    @Autowired
    private final StreamingPlatformRepository streamingPlatformRepository;
    
    public StreamingPlatformDTO createStreamingPlatform(StreamingPlatformRequestDTO streamingPlatformRequest) {
        return streamingPlatformRepository.createStreamingPlatform(streamingPlatformRequest);
    }

    public List<StreamingPlatformDTO> getAllStreamingPlatforms() {
        return streamingPlatformRepository.getAllStreamingPlatfroms();
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
