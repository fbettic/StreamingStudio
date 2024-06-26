package ar.edu.ubp.rest.portal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ar.edu.ubp.rest.portal.beans.response.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingClickRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.SubscriberAdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.MessageResponseDTO;
import ar.edu.ubp.rest.portal.dto.response.SubscriberAdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.response.SubscriberAdvertisingResponseDTO;
import ar.edu.ubp.rest.portal.repositories.AdvertisingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertisingService {
    @Autowired

    private final AdvertisingRepository advertisingRepository;
    @Autowired
    private final AdvertiserApiClientService advertiserApiClientService;

    @Autowired
    private final TargetServices targetServices;

    private void getBannerData(AdvertisingRequestDTO advertisingRequest) {
        if (advertisingRequest.getReferenceId() != null && advertisingRequest.getReferenceId() > 0) {
            AdvertisingResponseBean data = advertiserApiClientService.getAdvertisingData(
                    advertisingRequest.getReferenceId(),
                    advertisingRequest.getAdvertiserId());

            advertisingRequest.setBannerText(data.getText());
            advertisingRequest.setImageUrl(data.getImageUrl());
            advertisingRequest.setRedirectUrl(data.getRedirectUrl());
        }
    }

    // Advertisings
    public AdvertisingDTO createAdvertising(AdvertisingRequestDTO advertisingRequest) throws JsonProcessingException {

        getBannerData(advertisingRequest);

        AdvertisingDTO response = advertisingRepository.createAdvertising(advertisingRequest);

        if (!Objects.isNull(response)) {
            targetServices.updateAdvertisingTargetsFromJson(advertisingRequest.getTargets(),
                    response.getAdvertisingId());
        }
        return response;
    }

    public AdvertisingDTO updateAdvertisingById(Integer id, AdvertisingRequestDTO advertisingRequest)
            throws JsonProcessingException {
        getBannerData(advertisingRequest);

        AdvertisingDTO response = advertisingRepository.updateAdvertisingById(id, advertisingRequest);

        if (!Objects.isNull(response)) {
            targetServices.updateAdvertisingTargetsFromJson(advertisingRequest.getTargets(),
                    response.getAdvertisingId());
        }
        return response;
    }

    public AdvertisingDTO getAdvertisingById(Integer id) {
        return advertisingRepository.getAdvertisingById(id);
    }

    public List<AdvertisingDTO> getAllAdvertisingsByAdvertiser(Integer id) {
        return advertisingRepository.getAllAdvertisingsByAdvertiser(id);
    }

    public List<AdvertisingDTO> getAllAdvertisings() {
        return advertisingRepository.getAllAdvertisings();
    }

    public Integer deleteAdvertisingById(Integer id) {
        return advertisingRepository.deleteAdvertisingById(id);
    }

    public MessageResponseDTO createSubscriberAdvertisingClick(Integer subscriberId, AdvertisingClickRequestDTO click) {
        return new MessageResponseDTO(advertisingRepository.createSubscriberAdvertisingClick(subscriberId, click));
    }

    public List<SubscriberAdvertisingResponseDTO> getAdvertisingsForSubscriber(Boolean allPages, Integer subscriberId,
            List<SubscriberAdvertisingRequestDTO> request) {

                List<SubscriberAdvertisingDTO> advertisings = advertisingRepository
                .getAllAdvertisingForSubscriber(subscriberId);

        List<SubscriberAdvertisingResponseDTO> response = new ArrayList<>();

        for (SubscriberAdvertisingRequestDTO requestItem : request) {
            SubscriberAdvertisingResponseDTO responseItem = new SubscriberAdvertisingResponseDTO();

            responseItem.setSizeType(requestItem.getSizeType());
            responseItem.setSlotId(requestItem.getSlotId());

            // se filtran los anuncios por el tipo de tamaño especificado
            List<SubscriberAdvertisingDTO> filteredAdvertisings = filterAdvertisingsBySize(advertisings,
                    requestItem.getSizeType());

            // se obtienen los anuncios no seleccionados previamente
            List<SubscriberAdvertisingDTO> availableAdvertisings = getAvailableAdvertisings(allPages, response,
                    filteredAdvertisings);

            // si hay anuncios nuevos disponibles
            if (!availableAdvertisings.isEmpty()) {
                // Selecciona un anuncio entre los disponibles
                selectAdvertising(responseItem, availableAdvertisings);
            } else { // si no
                     // Seleccionar un anuncio random entre todos los anuncios existentes
                selectRandomAdvertising(responseItem, filteredAdvertisings);
            }

            response.add(responseItem);
        }
        return response;
    }

    private List<SubscriberAdvertisingDTO> filterAdvertisingsBySize(List<SubscriberAdvertisingDTO> advertisings,
            String sizeType) {
        List<SubscriberAdvertisingDTO> filteredAdvertisings = new ArrayList<>(advertisings);
        filteredAdvertisings.removeIf(ad -> !sizeType.equals(ad.getSizeType()));
        return filteredAdvertisings;
    }

    private List<SubscriberAdvertisingDTO> getAvailableAdvertisings(Boolean allPages,
            List<SubscriberAdvertisingResponseDTO> response, List<SubscriberAdvertisingDTO> filteredAdvertisings) {
        List<SubscriberAdvertisingDTO> availableAdvertisings = new ArrayList<>(filteredAdvertisings);
        if (allPages) {
            availableAdvertisings.removeIf(ad -> !ad.getAllPages());
        }

        for (SubscriberAdvertisingResponseDTO resp : response) {
            if (resp.getAdvertising() != null) {
                availableAdvertisings.removeIf(
                        ad -> ad.getAdvertisingId().equals(resp.getAdvertising().getAdvertisingId())
                                || (allPages && !ad.getAllPages()));
            }
        }
        return availableAdvertisings;
    }

    private void selectAdvertising(SubscriberAdvertisingResponseDTO responseItem,
            List<SubscriberAdvertisingDTO> availableAdvertisings) {

        List<SubscriberAdvertisingDTO> equalPointAdvertisings = new ArrayList<>();
        for (SubscriberAdvertisingDTO ad : availableAdvertisings) {
            if (Objects.isNull(responseItem.getAdvertising())
                    || responseItem.getAdvertising().getPoints() < ad.getPoints()) {
                responseItem.setAdvertising(ad);
                equalPointAdvertisings.clear();
                equalPointAdvertisings.add(ad);

            } else if (responseItem.getAdvertising().getPoints().equals(ad.getPoints())) {
                equalPointAdvertisings.add(ad);
            }
        }

        if (!equalPointAdvertisings.isEmpty()) {
            Integer random = new Random().nextInt(equalPointAdvertisings.size());
            responseItem.setAdvertising(equalPointAdvertisings
                    .get(random));
        }
    }

    private void selectRandomAdvertising(SubscriberAdvertisingResponseDTO responseItem,
            List<SubscriberAdvertisingDTO> filteredAdvertisings) {

        if (!filteredAdvertisings.isEmpty()) {
            SubscriberAdvertisingDTO selectedAd = filteredAdvertisings
                    .get(new Random().nextInt(filteredAdvertisings.size()));
            responseItem.setAdvertising(selectedAd);
        }
    }

}
