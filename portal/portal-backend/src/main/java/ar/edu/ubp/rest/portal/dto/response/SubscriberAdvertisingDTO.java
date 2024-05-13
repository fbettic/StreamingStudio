package ar.edu.ubp.rest.portal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberAdvertisingDTO {
    private Integer advertisingId;
    private String redirectUrl;
    private String imageUrl;
    private String bannerText;
    private String sizeType;
    private Integer height;
    private Integer width;
    private Boolean allPages;
    private Integer points;
}
