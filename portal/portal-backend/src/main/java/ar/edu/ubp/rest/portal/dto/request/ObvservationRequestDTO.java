package ar.edu.ubp.rest.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObvservationRequestDTO {
    private Integer platformId;
    private Integer subscriberId;
    private Integer transactionId;
    private String obvservation;
}
