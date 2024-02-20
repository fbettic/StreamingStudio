package ar.edu.ubp.rest.portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ar.edu.ubp.rest.portal.model.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator,Integer>{
    Optional<Administrator> findByEmail(@Param("Email") String email);
}