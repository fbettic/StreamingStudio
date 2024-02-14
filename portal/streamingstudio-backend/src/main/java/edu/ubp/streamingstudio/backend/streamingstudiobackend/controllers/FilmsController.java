package edu.ubp.streamingstudio.backend.streamingstudiobackend.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ubp.streamingstudio.backend.streamingstudiobackend.beans.FilmBean;
import edu.ubp.streamingstudio.backend.streamingstudiobackend.repositories.FilmsRepository;
import edu.ubp.streamingstudio.backend.streamingstudiobackend.security.beans.CreateUserBean;
import edu.ubp.streamingstudio.backend.streamingstudiobackend.security.models.ERole;
import edu.ubp.streamingstudio.backend.streamingstudiobackend.security.models.RoleEntity;
import edu.ubp.streamingstudio.backend.streamingstudiobackend.security.models.UserEntity;
import edu.ubp.streamingstudio.backend.streamingstudiobackend.security.repositories.UserRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
public class FilmsController {

    @Autowired
    FilmsRepository filmsRepository;
    @Autowired
    UserRepository userRepository;
@Autowired
PasswordEncoder passwordEncoder;
    

    @GetMapping(path = "/films")
    public ResponseEntity<List<FilmBean>> getFilms() {
        System.out.println("---------------> GET: Films <---------------");
        return new ResponseEntity<>(filmsRepository.getFilms(), HttpStatus.OK);
    }

    @GetMapping(path = "/test1")
    public ResponseEntity<String> getTest1() {
        return new ResponseEntity<>("Helloo! :D, este endpoint NO es seguro", HttpStatus.OK);
    }

    @GetMapping(path = "/test2")
    public ResponseEntity<String> getTest2() {
        return new ResponseEntity<>("Helloo! :D, este endpoint SI es seguro", HttpStatus.OK);
    }

    @PostMapping(path = "/createUser")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody CreateUserBean createUser) {

        Set<RoleEntity> roles = createUser.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUser.getUsername())
                .password(passwordEncoder.encode(createUser.getPassword()))
                .email(createUser.getEmail())
                .roles(roles).build();

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping(path = "/deleteUser")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "El usuario " + id + " ha sido elminado exitosamente";
    }

}
