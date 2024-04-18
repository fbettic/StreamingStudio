package ar.edu.ubp.rest.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SizeTypeDTO {
    private Integer sizeId;
    private String sizeType;
    private Integer sizeValue;
    private Integer height;
    private Integer width;
    private Integer sizeFeeId;
    private Integer sizeFee;
}
