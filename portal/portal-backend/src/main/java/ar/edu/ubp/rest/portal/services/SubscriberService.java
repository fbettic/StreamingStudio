package ar.edu.ubp.rest.portal.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ar.edu.ubp.rest.portal.dto.SubscriberDTO;
import ar.edu.ubp.rest.portal.dto.request.SubscriberRequestDTO;
import ar.edu.ubp.rest.portal.repositories.SubscriberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriberService {
    @Autowired
    private final SubscriberRepository subscriberRepository;

    @Autowired
    private final TargetServices targetServices;

    public SubscriberDTO updateSubscriber(Integer id, SubscriberRequestDTO subscriber) throws JsonProcessingException {
        SubscriberDTO response = subscriberRepository.updateSubscriber(id, subscriber);

        if (Objects.nonNull(response)) {
            targetServices.updateMarketingPreferencesFromJson(subscriber.getMarketingPreferences(), id);
        }

        return response;
    }

    public SubscriberDTO getSubscriberById(Integer id) {
        return subscriberRepository.getSubscriberById(id);
    }
}
