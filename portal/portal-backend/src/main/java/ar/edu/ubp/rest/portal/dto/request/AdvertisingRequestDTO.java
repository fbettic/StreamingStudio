package ar.edu.ubp.rest.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisingRequestDTO {
    private Integer advertiserId;
    private Integer sizeId;
    private Integer allPagesFeeId;
    private Integer priorityId;
    private String redirectUrl;
    private String imageUrl;
    private String bannerText;
    private Integer bannerId;
    private String fromDate;
    private String toDate;
}
