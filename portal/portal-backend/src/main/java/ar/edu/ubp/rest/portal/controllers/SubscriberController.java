package ar.edu.ubp.rest.portal.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.ubp.rest.portal.dto.SubscriberDTO;
import ar.edu.ubp.rest.portal.dto.TargetCategoryDTO;
import ar.edu.ubp.rest.portal.dto.request.SubscriberRequestDTO;
import ar.edu.ubp.rest.portal.dto.request.TargetRequestDTO;
import ar.edu.ubp.rest.portal.models.users.CustomUserDetails;
import ar.edu.ubp.rest.portal.services.SubscriberService;
import ar.edu.ubp.rest.portal.services.TargetServices;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class SubscriberController {

    @Autowired
    private final TargetServices targetServices;

    @Autowired
    private final SubscriberService subscriberService;

    private Integer getCurrentUserId() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Integer userId = userDetails.getId();
            return userId;
        } else {
            throw new Exception("User id not found");
        }
    }

    @PutMapping("subscribers")
    public ResponseEntity<SubscriberDTO> updateSubscriber(@RequestBody SubscriberRequestDTO subscriber)
            throws Exception {

        return ResponseEntity.ok(subscriberService.updateSubscriber(getCurrentUserId(), subscriber));
    }

    @GetMapping("subscribers")
    public ResponseEntity<SubscriberDTO> getSubcriberById() throws Exception {
        Integer id = getCurrentUserId();
        SubscriberDTO response = subscriberService.getSubscriberById(id);
        List<TargetCategoryDTO> targets = targetServices.getAllMarketingPreferencesBySubscriberId(id);

        List<Integer> targetsId = new ArrayList<>();
        if (Objects.nonNull(targets)) {
            for (TargetCategoryDTO target : targets) {
                targetsId.add(target.getTargetId());
            }
        }

        response.setMarketingPreferences(targetsId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("targets")
    public ResponseEntity<TargetCategoryDTO> createTargetCategory(@RequestBody TargetRequestDTO targetRequest) {
        return ResponseEntity.ok(targetServices.createTargetCategory(targetRequest.getTargetTitle()));
    }

    @GetMapping("targets")
    public ResponseEntity<List<TargetCategoryDTO>> getAllTargetCategories() {
        return ResponseEntity.ok(targetServices.getAllTargetCategories());
    }

    @PutMapping("targets/{id}")
    public ResponseEntity<TargetCategoryDTO> updateTargetCategory(@PathVariable Integer id,
            @RequestBody TargetRequestDTO targetRequest) {

        return ResponseEntity.ok(targetServices.updateTargetCategory(id, targetRequest.getTargetTitle()));
    }

    @DeleteMapping("targets/{id}")
    public ResponseEntity<Integer> deleteTargetCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(targetServices.deleteTargetCategory(id));
    }

}
