package ar.edu.ubp.rest.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerDTO {
    private Integer advertiserId;
    private Integer referenceId;
    private String text;
    private String imageUrl;
    private String redirectUrl;
}
