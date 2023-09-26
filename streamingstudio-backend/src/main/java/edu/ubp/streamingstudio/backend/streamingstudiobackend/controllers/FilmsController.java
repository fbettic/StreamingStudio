package edu.ubp.streamingstudio.backend.streamingstudiobackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ubp.streamingstudio.backend.streamingstudiobackend.beans.FilmBean;
import edu.ubp.streamingstudio.backend.streamingstudiobackend.repositories.FilmsRepository;

@RestController
@RequestMapping(path = "/api", produces={MediaType.APPLICATION_JSON_VALUE})
public class FilmsController {

    @Autowired
    FilmsRepository reository;

    @GetMapping(path="/films")
    public ResponseEntity<List<FilmBean>> getFilms(){
        System.out.println("---------------> CALL <---------------");
        return new ResponseEntity<>(reository.getFilms(), HttpStatus.OK);
    }

    @GetMapping(path="/test")
    public ResponseEntity<String> getTest(){
        return new ResponseEntity<>("Helloo! :D", HttpStatus.OK);
    }
}
