package ar.edu.ubp.rest.portal.dto.request;

import ar.edu.ubp.rest.portal.dto.AdvertisingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberAdvertisingRequestDTO {
    private String slotId;
    private String sizeType;
    private AdvertisingDTO advertising;
}
