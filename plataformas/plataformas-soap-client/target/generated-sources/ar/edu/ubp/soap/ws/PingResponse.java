
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para pingResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="pingResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="pong" type="{http://ws.soap.ubp.edu.ar/}basicResponseBean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pingResponse", propOrder = {
    "pong"
})
public class PingResponse {

    protected BasicResponseBean pong;

    /**
     * Obtiene el valor de la propiedad pong.
     * 
     * @return
     *     possible object is
     *     {@link BasicResponseBean }
     *     
     */
    public BasicResponseBean getPong() {
        return pong;
    }

    /**
     * Define el valor de la propiedad pong.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicResponseBean }
     *     
     */
    public void setPong(BasicResponseBean value) {
        this.pong = value;
    }

}
