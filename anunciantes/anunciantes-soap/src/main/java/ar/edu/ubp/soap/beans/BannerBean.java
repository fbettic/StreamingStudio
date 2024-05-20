package ar.edu.ubp.soap.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerBean {
    private Integer bannerId;
    private String text;
    private String imageUrl;
    private String redirectUrl;
}
