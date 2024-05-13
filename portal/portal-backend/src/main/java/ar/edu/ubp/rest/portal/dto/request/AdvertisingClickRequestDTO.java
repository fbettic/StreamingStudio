package ar.edu.ubp.rest.portal.dto.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisingClickRequestDTO {
    private Integer advertisingId;
    private Date clickedAt;
}
