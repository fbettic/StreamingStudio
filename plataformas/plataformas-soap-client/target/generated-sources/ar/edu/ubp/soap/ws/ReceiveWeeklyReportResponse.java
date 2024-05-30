
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para receiveWeeklyReportResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="receiveWeeklyReportResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="report" type="{http://ws.soap.ubp.edu.ar/}basicResponseBean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "receiveWeeklyReportResponse", propOrder = {
    "report"
})
public class ReceiveWeeklyReportResponse {

    protected BasicResponseBean report;

    /**
     * Obtiene el valor de la propiedad report.
     * 
     * @return
     *     possible object is
     *     {@link BasicResponseBean }
     *     
     */
    public BasicResponseBean getReport() {
        return report;
    }

    /**
     * Define el valor de la propiedad report.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicResponseBean }
     *     
     */
    public void setReport(BasicResponseBean value) {
        this.report = value;
    }

}
