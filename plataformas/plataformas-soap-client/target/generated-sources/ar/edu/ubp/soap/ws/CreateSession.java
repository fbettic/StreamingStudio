
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para createSession complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="createSession">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="newSession" type="{http://ws.soap.ubp.edu.ar/}newSessionBean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createSession", propOrder = {
    "newSession"
})
public class CreateSession {

    protected NewSessionBean newSession;

    /**
     * Obtiene el valor de la propiedad newSession.
     * 
     * @return
     *     possible object is
     *     {@link NewSessionBean }
     *     
     */
    public NewSessionBean getNewSession() {
        return newSession;
    }

    /**
     * Define el valor de la propiedad newSession.
     * 
     * @param value
     *     allowed object is
     *     {@link NewSessionBean }
     *     
     */
    public void setNewSession(NewSessionBean value) {
        this.newSession = value;
    }

}
