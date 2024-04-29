package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.request.BasicPayloadBean;
import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.AuthResponseDTO;
import ar.edu.ubp.rest.portal.repositories.AdvertiserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertiserService {

    @Autowired
    private final AdvertiserRepository advertiserRepository;

    @Autowired
    private final AdvertiserApiClientService advertiserApiClientService;

    @Autowired
    private final AuthService authService;

    public String pingAdvertiser(AdvertiserRequestDTO advertiser) throws Exception {
        return advertiserApiClientService.ping(advertiser.getCompanyName(),
                advertiser.getServiceType(), advertiser.getApiUrl(),
                new BasicPayloadBean(advertiser.getAuthToken()));
    }

    public AuthResponseDTO createAdvertiser(AdvertiserRequestDTO advertiserRequest) throws Exception {

        if (!advertiserRequest.getServiceType().equals("ACCOUNT")) {
            String result = advertiserApiClientService.ping(advertiserRequest.getCompanyName(),
                    advertiserRequest.getServiceType(), advertiserRequest.getApiUrl(),
                    new BasicPayloadBean(advertiserRequest.getAuthToken()));

            if (!result.equals("pong")) {
                throw new Exception(
                        "Failed to establish a connection with the specified API. Please verify the accuracy of the connection details provided.");
            }
        }

        return authService.createAdvertiser(advertiserRequest);
    }

    public AdvertiserDTO updateAdvertiserById(Integer id, AdvertiserRequestDTO advertiserRequest) {
        return advertiserRepository.updateAdvertiserById(advertiserRequest, id);
    }

    public List<AdvertiserDTO> getAllAdvertisers() {
        return advertiserRepository.getAllAdvertisers();
    }

    public AdvertiserDTO getAdvertiserById(Integer id) {
        return advertiserRepository.getAdvertiserById(id);
    }


    public Integer deleteAdvertiserById(Integer id) {
        return advertiserRepository.deleteAdvertiserById(id);
    }

}
