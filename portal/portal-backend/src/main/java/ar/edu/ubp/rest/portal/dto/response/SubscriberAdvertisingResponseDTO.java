package ar.edu.ubp.rest.portal.dto.response;

import ar.edu.ubp.rest.portal.dto.request.SubscriberAdvertisingRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberAdvertisingResponseDTO extends SubscriberAdvertisingRequestDTO {
    private SubscriberAdvertisingDTO advertising;

    @Builder(builderMethodName = "subscriberAdvertisingResponse")
    public SubscriberAdvertisingResponseDTO(
            final String slotId,
            final String sizeType,
            SubscriberAdvertisingDTO advertising) {
        super(slotId, sizeType);
        this.advertising = advertising;
    }
}
