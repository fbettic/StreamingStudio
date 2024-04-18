package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.FeeDTO;
import ar.edu.ubp.rest.portal.dto.request.FeeRequestDTO;

public interface IFeeRepository {
    public FeeDTO createFee(FeeRequestDTO fee);
    public FeeDTO updateFee(FeeRequestDTO fee, Integer id);
    public FeeDTO getFeeById(Integer id);
    public List<FeeDTO> getAllFees();
    public Integer deleteFeeById(Integer id);
}
