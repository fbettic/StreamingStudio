package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.dto.SubscriberDTO;
import ar.edu.ubp.rest.portal.models.users.Subscriber;

public interface ISubscriberRepository {
    public SubscriberDTO getSubscriberByEmail(String email);

    public SubscriberDTO createSubscriber(Subscriber subscriber);
}
