package ar.edu.ubp.rest.portal.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.beans.AdvertisingResponseBean;
import ar.edu.ubp.rest.portal.beans.AuthTokenRequestBean;
import ar.edu.ubp.rest.portal.dto.AdvertiserDTO;
import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import ar.edu.ubp.rest.portal.dto.CountryDTO;
import ar.edu.ubp.rest.portal.dto.FilmDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertiserRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.AdvertisingRequestDTO;
import ar.edu.ubp.rest.portal.dto.response.AuthResponseDTO;
import ar.edu.ubp.rest.portal.services.AdministratorService;
import ar.edu.ubp.rest.portal.services.AdvertiserClientService;
import ar.edu.ubp.rest.portal.services.AuthService;
import ar.edu.ubp.rest.portal.services.BatchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class AdministratorController {
    @Autowired
    private final AuthService authService;
    @Autowired
    private final AdministratorService administratorServices;
    @Autowired
    private final AdvertiserClientService advertiserClientService;

    @Autowired
    private BatchService batchService;

    @GetMapping("advertisers")
    public ResponseEntity<List<AdvertiserDTO>> getAllAdvertisers() {
        return ResponseEntity.ok(administratorServices.getAllAdvertisers());
    }

    @PostMapping("advertisers")
    public ResponseEntity<AuthResponseDTO> createAdvertiser(@RequestBody AdvertiserRequestDTO advertiserData)
            throws Exception {

        if (!advertiserData.getServiceType().equals("ACCOUNT")) {
            String result = advertiserClientService.pingAdvertiser(advertiserData.getCompanyName(),
                    advertiserData.getServiceType(), advertiserData.getApiUrl(),
                    new AuthTokenRequestBean(advertiserData.getAuthToken()));

            if (!result.equals("pong")) {
                throw new Exception(
                        "Failed to establish a connection with the specified API. Please verify the accuracy of the connection details provided.");
            }
        }

        return ResponseEntity.ok(authService.createAdvertiser(advertiserData));
    }

    @PutMapping(path = "advertisers/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<AdvertiserDTO> updateAdvertiserById(@PathVariable Integer id,
            @RequestBody AdvertiserRequestDTO advertiserData) {
        return new ResponseEntity<>(administratorServices.updateAdvertiserById(id, advertiserData), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "advertisers/{id}")
    public ResponseEntity<Integer> deleteAdvertiserById(@PathVariable Integer id) {
        return new ResponseEntity<>(administratorServices.deleteAdvertiserById(id), HttpStatus.CREATED);
    }

    @PostMapping("countries")
    public ResponseEntity<String> loadAllCountryes(@RequestBody List<CountryDTO> countries) {
        return new ResponseEntity<String>(administratorServices.loadAllCountryes(countries), HttpStatus.CREATED);
    }

    @GetMapping("films")
    public ResponseEntity<List<FilmDTO>> getAllFilms() {
        return ResponseEntity.ok(administratorServices.getAllFilms());
    }

    @PostMapping("advertisings")
    public ResponseEntity<AdvertisingDTO> createAdvertising(@RequestBody AdvertisingRequestDTO advertisingRequest) {
        System.out.println("----------------> "+ advertisingRequest.toString());
        return ResponseEntity.ok(administratorServices.createAdvertising(advertisingRequest));
    }

    @GetMapping("advertisings")
    public ResponseEntity<List<AdvertisingDTO>> getAllAdvertisings() {
        return ResponseEntity.ok(administratorServices.getAllAdvertisings());
    }

    @PostMapping("ping")
    public ResponseEntity<String> pingAdvertiser(@RequestBody AdvertiserRequestDTO advertiser)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        return new ResponseEntity<String>(advertiserClientService.pingAdvertiser(advertiser.getCompanyName(),
                advertiser.getServiceType(), advertiser.getApiUrl(),
                new AuthTokenRequestBean(advertiser.getAuthToken())), HttpStatus.OK);
    }

    @GetMapping("client/advertisings")
    public ResponseEntity<?> getAllAdvertisingsFromClients() {
        try {
            batchService.updateAdvertisings(advertiserClientService.getAllAdvertisingsFromClients());
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
            return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<String>("hello", HttpStatus.CREATED);
    }
}
