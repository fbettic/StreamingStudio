package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.StreamingPlatformDTO;
import ar.edu.ubp.rest.portal.dto.request.StreamingPlatformRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.StreamingPlatformSubscriberResponseDTO;

public interface IStreamingPlatformRepository {
    public StreamingPlatformDTO createStreamingPlatform(StreamingPlatformRequestDTO streamingPlatfromRequest);

    public StreamingPlatformDTO getStreamingPlatformById(Integer id);

    public List<StreamingPlatformDTO> getAllStreamingPlatfroms();

    public List<StreamingPlatformSubscriberResponseDTO> getAllStreamingPlatformsBySubscriberId(Integer subscriberId);

    public Integer deleteStreamingPlatfromById(Integer id);

    public StreamingPlatformDTO updateStreamingPlatform(StreamingPlatformRequestDTO streamingPlatfromRequest,
            Integer id);
}
