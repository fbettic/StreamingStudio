package ar.edu.ubp.rest.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SizeTypeRequestDTO {
    private String sizeType;
    private Integer sizeValue;
    private Integer height;
    private Integer width;
    private Integer sizeFeeId;
}
