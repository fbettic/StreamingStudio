package ar.edu.ubp.rest.portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import ar.edu.ubp.rest.portal.dto.FeeDTO;
import ar.edu.ubp.rest.portal.dto.request.FeeRequestDTO;
import ar.edu.ubp.rest.portal.services.FeeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/invoicing/")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:4200" })
public class InvoicingController {
    
    @Autowired
    private final FeeService feeService;

    @PostMapping("fees")
    public ResponseEntity<FeeDTO> createFee(@RequestBody FeeRequestDTO FeeRequest)
            throws Exception {

        return ResponseEntity.ok(feeService.createFee(FeeRequest));
    }

    @GetMapping("fees")
    public ResponseEntity<List<FeeDTO>> getAllFees() throws Exception {
        return ResponseEntity.ok(feeService.getAllFees());
    }

    @GetMapping("fees/{id}")
    public ResponseEntity<FeeDTO> getFeeById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(feeService.getFeeById(id));
    }

    @PutMapping("fees/{id}")
    public ResponseEntity<FeeDTO> updateFee(@PathVariable Integer id,
            @RequestBody FeeRequestDTO sizeTypeRequest) throws Exception {
        return ResponseEntity.ok(feeService.updateFee(sizeTypeRequest, id));
    }

    @DeleteMapping("fees/{id}")
    public ResponseEntity<Integer> deleteFeeById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(feeService.deleteFeeById(id));
    }
}
