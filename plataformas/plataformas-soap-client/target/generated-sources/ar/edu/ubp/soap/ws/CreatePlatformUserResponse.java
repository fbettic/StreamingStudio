
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para createPlatformUserResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="createPlatformUserResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="platformUser" type="{http://ws.soap.ubp.edu.ar/}platformUserBean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createPlatformUserResponse", propOrder = {
    "platformUser"
})
public class CreatePlatformUserResponse {

    protected PlatformUserBean platformUser;

    /**
     * Obtiene el valor de la propiedad platformUser.
     * 
     * @return
     *     possible object is
     *     {@link PlatformUserBean }
     *     
     */
    public PlatformUserBean getPlatformUser() {
        return platformUser;
    }

    /**
     * Define el valor de la propiedad platformUser.
     * 
     * @param value
     *     allowed object is
     *     {@link PlatformUserBean }
     *     
     */
    public void setPlatformUser(PlatformUserBean value) {
        this.platformUser = value;
    }

}
