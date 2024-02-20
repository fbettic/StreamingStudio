package ar.edu.ubp.rest.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor 
public class PortalController {

    @GetMapping("/gettest")
    public String getTest() {
        return "Helloo! :D, este endpoint necesita login";
    }

    @PostMapping("/posttest")
    public String postTest() {
        return "Helloo! :D, este endpoint necesita login";
    }
    
}
