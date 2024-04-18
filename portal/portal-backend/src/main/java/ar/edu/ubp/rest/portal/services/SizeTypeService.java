package ar.edu.ubp.rest.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.ubp.rest.portal.dto.SizeTypeDTO;
import ar.edu.ubp.rest.portal.dto.request.SizeTypeRequestDTO;
import ar.edu.ubp.rest.portal.repositories.SizeTypeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SizeTypeService {
    @Autowired
    private final SizeTypeRepository sizeTypeRepository;

    public SizeTypeDTO createSizeType(SizeTypeRequestDTO size) {
        return sizeTypeRepository.createSizeType(size);
    }

    public SizeTypeDTO updateSizeType(SizeTypeRequestDTO size, Integer id) {
        return sizeTypeRepository.updateSizeType(size, id);
    }

    public SizeTypeDTO getSizeTypeById(Integer id) {
        return sizeTypeRepository.getSizeTypeById(id);
    }

    public List<SizeTypeDTO> getAllSizeTypes() {
        return sizeTypeRepository.getAllSizeTypes();
    }

    public Integer deleteSizeTypeById(Integer id) {
        return sizeTypeRepository.deleteSizeTypeById(id);
    }
}
