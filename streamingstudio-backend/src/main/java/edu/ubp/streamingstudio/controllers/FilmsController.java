package edu.ubp.streamingstudio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ubp.streamingstudio.backend.streamingstudiobackend.beans.FilmBean;

@RestController
@RequestMapping(path = "films", produces={MediaType.APPLICATION_JSON_VALUE})
public class FilmsController {

}
