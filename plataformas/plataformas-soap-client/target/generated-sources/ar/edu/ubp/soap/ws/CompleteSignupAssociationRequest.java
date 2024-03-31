
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para completeSignupAssociationRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="completeSignupAssociationRequest">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="newPlatformUser" type="{http://ws.soap.ubp.edu.ar/}newPlatformUserBean" minOccurs="0"/>
 *         <element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "completeSignupAssociationRequest", propOrder = {
    "newPlatformUser",
    "uuid"
})
public class CompleteSignupAssociationRequest {

    protected NewPlatformUserBean newPlatformUser;
    protected String uuid;

    /**
     * Obtiene el valor de la propiedad newPlatformUser.
     * 
     * @return
     *     possible object is
     *     {@link NewPlatformUserBean }
     *     
     */
    public NewPlatformUserBean getNewPlatformUser() {
        return newPlatformUser;
    }

    /**
     * Define el valor de la propiedad newPlatformUser.
     * 
     * @param value
     *     allowed object is
     *     {@link NewPlatformUserBean }
     *     
     */
    public void setNewPlatformUser(NewPlatformUserBean value) {
        this.newPlatformUser = value;
    }

    /**
     * Obtiene el valor de la propiedad uuid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Define el valor de la propiedad uuid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

}
