package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.dto.FeeDTO;
import ar.edu.ubp.rest.portal.dto.request.FeeRequestDTO;
import ar.edu.ubp.rest.portal.repositories.FeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeeService {
    @Autowired
    private final FeeRepository feeRepository;

    public FeeDTO createFee(FeeRequestDTO fee) {
        return feeRepository.createFee(fee);
    }

    public FeeDTO updateFee(FeeRequestDTO fee, Integer id) {
        return feeRepository.updateFee(fee, id);
    }

    public FeeDTO getFeeById(Integer id) {
        return feeRepository.getFeeById(id);
    }

    public List<FeeDTO> getAllFees() {
        return feeRepository.getAllFees();
    }

    public Integer deleteFeeById(Integer id) {
        return feeRepository.deleteFeeById(id);
    }
}
