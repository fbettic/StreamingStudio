package ar.edu.ubp.rest.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerPriorityDTO {
    private Integer priorityId;
    private String priorityType;
    private Integer priorityValue;
    private Integer priorityFeeId;
    private Float priorityFee;
}
