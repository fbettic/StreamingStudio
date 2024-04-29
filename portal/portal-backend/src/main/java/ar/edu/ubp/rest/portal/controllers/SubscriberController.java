package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.TargetCategoryDTO;
import ar.edu.ubp.rest.portal.dto.request.TargetRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.FilmSubscriberResponseDTO;
import ar.edu.ubp.rest.portal.services.TargetServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class SubscriberController {

    @Autowired
    private final TargetServices targetServices;

    @PostMapping("targets")
    public ResponseEntity<TargetCategoryDTO> createTargetCategory(@RequestBody TargetRequestDTO targetRequest) {
        return ResponseEntity.ok(targetServices.createTargetCategory(targetRequest.getTargetTitle()));
    }

    @GetMapping("targets")
    public ResponseEntity<List<TargetCategoryDTO>> getAllTargetCategories() {
        return ResponseEntity.ok(targetServices.getAllTargetCategories());
    }

    @PutMapping("targets/{id}")
    public ResponseEntity<TargetCategoryDTO> updateTargetCategoryTitle(@PathVariable Integer id,
            @RequestBody TargetRequestDTO targetRequest) {

        return ResponseEntity.ok(targetServices.updateTargetCategoryTitle(id, targetRequest.getTargetTitle()));
    }

    

}
