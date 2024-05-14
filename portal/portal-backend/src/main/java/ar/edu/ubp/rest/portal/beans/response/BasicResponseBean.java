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
public class BasicResponseBean extends ServiceResponseBean {
    private String response;

    public BasicResponseBean(Element reportElement) {
        this.response = getElementValue(reportElement, "response");
    }
}
