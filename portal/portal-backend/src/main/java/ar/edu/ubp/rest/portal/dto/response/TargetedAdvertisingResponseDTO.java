package ar.edu.ubp.rest.portal.dto.response;

import java.util.List;

import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetedAdvertisingResponseDTO {
    private AdvertisingDTO top;
    private List<List<AdvertisingDTO>> sides;
} 
