package ar.edu.ubp.rest.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerPriorityRequestDTO {
    private String priorityType;
    private Integer priorityValue;
    private Integer priorityFeeId;
}
