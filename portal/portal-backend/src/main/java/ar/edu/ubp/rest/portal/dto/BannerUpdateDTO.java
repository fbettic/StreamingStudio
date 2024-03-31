package ar.edu.ubp.rest.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerUpdateDTO {
    private Integer advertiserId;
    private Integer bannerId;
    private String text;
    private String imageUrl;
    private String redirectUrl;
}
