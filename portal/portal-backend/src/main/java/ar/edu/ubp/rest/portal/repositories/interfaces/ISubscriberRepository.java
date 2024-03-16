package ar.edu.ubp.rest.portal.repositories.interfaces;

import ar.edu.ubp.rest.portal.dto.SubscriberDTO;
import ar.edu.ubp.rest.portal.models.Subscriber;

public interface ISubscriberRepository {
    public SubscriberDTO findSubscriberByEmail(String email);

    public SubscriberDTO save(Subscriber subscriber);
}
