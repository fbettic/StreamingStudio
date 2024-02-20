package ar.edu.ubp.rest.portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ar.edu.ubp.rest.portal.model.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber,Integer>{
    Optional<Subscriber> findByEmail(@Param("Email") String email);
}