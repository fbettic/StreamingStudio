package ar.edu.ubp.rest.anunciantesrest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.anunciantesrest.beans.BannerBean;
import ar.edu.ubp.rest.anunciantesrest.repositories.AnunciantesRestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "rest/anunciantes", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AnunciantesController {
    @Autowired
    AnunciantesRestRepository repository;

    @GetMapping(path = "/banner/{bannerId}")
    public ResponseEntity<BannerBean> getBanner(@PathVariable("bannerId") String bannerId) {
        System.out.println("-----------------------> "+ bannerId +" <-----------------------");
        return new ResponseEntity<>(repository.getBanner(Integer.valueOf(bannerId)), HttpStatus.OK);
    }

    @GetMapping(path = "/test1")
    public ResponseEntity<String> getTest1() {
        return new ResponseEntity<>("Helloo! :D, este endpoint NO es seguro", HttpStatus.OK);
    }
}
