package ar.edu.ubp.rest.portal.beans.response;

import org.w3c.dom.Element;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BannerResponseBean extends ServiceResponseBean {
    private Integer bannerId;
    private String imageUrl;
    private String redirectUrl;
    private String text;

    public BannerResponseBean(Element bannerElement) {
        this.bannerId = Integer.parseInt(getElementValue(bannerElement, "bannerId"));
        this.imageUrl = getElementValue(bannerElement, "imageUrl");
        this.redirectUrl = getElementValue(bannerElement, "redirectUrl");
        this.text = getElementValue(bannerElement, "text");
    }
}
