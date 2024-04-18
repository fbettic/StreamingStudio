package ar.edu.ubp.rest.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeRequestDTO {
    private String feeType;
    private Float feeValue;
}
