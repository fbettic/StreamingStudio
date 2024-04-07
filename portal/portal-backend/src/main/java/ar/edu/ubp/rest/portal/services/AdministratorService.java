package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.beans.request.BasicPayloadBean;
import ar.edu.ubp.rest.portal.beans.response.BannerResponseBean;
import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.StreamingPlatformDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.StreamingPlatformRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.AuthResponseDTO;
import ar.edu.ubp.rest.portal.repositories.AdvertiserRepository;
import ar.edu.ubp.rest.portal.repositories.AdvertisingRepository;
import ar.edu.ubp.rest.portal.repositories.FilmRepository;
import ar.edu.ubp.rest.portal.repositories.StreamingPlatformRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministratorService {
    // Repositories
    @Autowired
    private final StreamingPlatformRepository streamingPlatformRepository;
    @Autowired
    private final FilmRepository filmRepository;
    @Autowired
    private final AdvertiserRepository advertiserRepository;
    @Autowired
    private final AdvertisingRepository advertisingRepository;

    // Services
    @Autowired
    private BatchService batchService;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final PlatformApiClientService platformApiClientService;
    @Autowired
    private final AdvertiserApiClientService advertiserApiClientService;

    // Advertiser management
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

    public List<AdvertiserDTO> getAllAdvertisers() {
        return advertiserRepository.getAllAdvertisers();
    }

    public Integer deleteAdvertiserById(Integer id) {
        return advertiserRepository.deleteAdvertiserById(id);
    }

    public AdvertiserDTO updateAdvertiserById(Integer id, AdvertiserRequestDTO advertiserRequest) {
        return advertiserRepository.updateAdvertiserById(advertiserRequest, id);
    }

    // Advertising management
    public AdvertisingDTO createAdvertising(AdvertisingRequestDTO advertisingRequest) {
        if (advertisingRequest.getBannerId() != null && advertisingRequest.getBannerId() > 0) {
            BannerResponseBean bannerData = advertiserApiClientService.getBannerById(advertisingRequest.getBannerId(),
                    advertisingRequest.getAdvertiserId());

            advertisingRequest.setBannerText(bannerData.getText());
            advertisingRequest.setImageUrl(bannerData.getImageUrl());
            advertisingRequest.setRedirectUrl(bannerData.getRedirectUrl());
        }

        return advertisingRepository.createAdvertising(advertisingRequest);
    }

    public String getAllAdvertisingsFromAdvertisers() throws Exception {
        try {
            batchService.updateAdvertisings(advertiserApiClientService.getAllAdvertisingsFromAdvertisers());
        } catch (Exception e) {
            throw new Exception(e);
        }
        return "Advertisings have been updated";
    }

    public List<AdvertisingDTO> getAllAdvertisings() {
        return advertisingRepository.getAllAdvertisings();
    }

    // Film management
    public StreamingPlatformDTO createStreamingPlatform(StreamingPlatformRequestDTO streamingPlatformRequest){
        return streamingPlatformRepository.createStreamingPlatform(streamingPlatformRequest);
    }

    public List<StreamingPlatformDTO> getAllStreamingPlatforms(){
        return streamingPlatformRepository.getAllStreamingPlatfroms();
    }

    public StreamingPlatformDTO getStreamingPlatformById(Integer platformId) {
        return streamingPlatformRepository.getStreamingPlatformById(platformId);
    }

    public Integer deleteStreamingPlatfromById(Integer id) {
        return streamingPlatformRepository.deleteStreamingPlatfromById(id);
    }

    public StreamingPlatformDTO updateStreamingPlatfromById(Integer id, StreamingPlatformRequestDTO streamingPlatformRequest) {
        return streamingPlatformRepository.updateStreamingPlatform(streamingPlatformRequest, id);
    }

    // Film management
    public String loadAllCountries(List<CountryDTO> countries) {
        int countriesLoaded = filmRepository.loadAllCountries(countries);

        if (countriesLoaded == 0) {
            return "No countries were created.";
        } else {
            return "Successfully loaded " + countriesLoaded + " countries.";
        }
    }

    public String getAllFilmsFromPlatforms() throws Exception {
        try {
            batchService.updateFilms(platformApiClientService.getAllFilmsFromPlatforms());
        } catch (Exception e) {
            throw new Exception(e);
        }
        return "Films have been updated";
    }

    public List<FilmDTO> getAllFilms() {
        return filmRepository.getAllFilms();
    }

}
