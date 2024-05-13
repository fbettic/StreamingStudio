package ar.edu.ubp.rest.portal.beans.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.w3c.dom.Document;
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
@EqualsAndHashCode(callSuper=false)
public class ReportResponseBean extends ServiceResponseBean{
    private String response;

    public ReportResponseBean(Document xmlDocument){
        Element e = (Element) xmlDocument.getElementsByTagName("associationRequest").item(0);
        this.response = getElementValue(e, "response");
    }
}
