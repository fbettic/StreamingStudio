package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.dto.SubscriberDTO;
import ar.edu.ubp.rest.portal.dto.request.SubscriberRequestDTO;
import ar.edu.ubp.rest.portal.models.users.Subscriber;

public interface ISubscriberRepository {

    public SubscriberDTO createSubscriber(Subscriber subscriber);

    public SubscriberDTO updateSubscriber(Integer id, SubscriberRequestDTO subscriber);

    public SubscriberDTO getSubscriberById(Integer id);

    public Integer deleteSubscriberById(Integer id);
}
