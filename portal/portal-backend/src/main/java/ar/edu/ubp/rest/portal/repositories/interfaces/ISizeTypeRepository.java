package ar.edu.ubp.rest.portal.repositories.interfaces;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.SizeTypeDTO;
import ar.edu.ubp.rest.portal.dto.request.SizeTypeRequestDTO;

public interface ISizeTypeRepository {
    public SizeTypeDTO createSizeType(SizeTypeRequestDTO size);
    public SizeTypeDTO updateSizeType(SizeTypeRequestDTO size, Integer id);
    public SizeTypeDTO getSizeTypeById(Integer id);
    public List<SizeTypeDTO> getAllSizeTypes();
    public Integer deleteSizeTypeById(Integer id);
}
