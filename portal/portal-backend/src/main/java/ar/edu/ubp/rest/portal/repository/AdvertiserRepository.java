package ar.edu.ubp.rest.portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ar.edu.ubp.rest.portal.model.Advertiser;

public interface AdvertiserRepository extends JpaRepository<Advertiser,Integer>{
    Optional<Advertiser> findByEmail(@Param("Email") String email);
}
