package edu.ubp.streamingstudio.backend.streamingstudiobackend.security.repositories;

import edu.ubp.streamingstudio.backend.streamingstudiobackend.security.models.UserEntity;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    
   
    Optional<UserEntity> findByUsername(String username);
    
    /* 
    @Query("select u from UserEntity u where u.username = ?1")
    Optional<UserEntity> getName(String username);
    */
}
