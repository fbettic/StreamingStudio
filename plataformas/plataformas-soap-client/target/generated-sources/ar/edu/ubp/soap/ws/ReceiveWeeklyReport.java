
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para receiveWeeklyReport complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="receiveWeeklyReport">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="report" type="{http://ws.soap.ubp.edu.ar/}weeklyReportBean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "receiveWeeklyReport", propOrder = {
    "report"
})
public class ReceiveWeeklyReport {

    protected WeeklyReportBean report;

    /**
     * Obtiene el valor de la propiedad report.
     * 
     * @return
     *     possible object is
     *     {@link WeeklyReportBean }
     *     
     */
    public WeeklyReportBean getReport() {
        return report;
    }

    /**
     * Define el valor de la propiedad report.
     * 
     * @param value
     *     allowed object is
     *     {@link WeeklyReportBean }
     *     
     */
    public void setReport(WeeklyReportBean value) {
        this.report = value;
    }

}
