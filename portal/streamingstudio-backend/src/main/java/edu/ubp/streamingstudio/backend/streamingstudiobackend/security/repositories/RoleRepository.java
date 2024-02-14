package edu.ubp.streamingstudio.backend.streamingstudiobackend.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.ubp.streamingstudio.backend.streamingstudiobackend.security.models.RoleEntity;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long>{
}
