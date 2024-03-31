
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para completeLoginAssociationRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="completeLoginAssociationRequest">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="loginRequest" type="{http://ws.soap.ubp.edu.ar/}loginRequestBean" minOccurs="0"/>
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
@XmlType(name = "completeLoginAssociationRequest", propOrder = {
    "loginRequest",
    "uuid"
})
public class CompleteLoginAssociationRequest {

    protected LoginRequestBean loginRequest;
    protected String uuid;

    /**
     * Obtiene el valor de la propiedad loginRequest.
     * 
     * @return
     *     possible object is
     *     {@link LoginRequestBean }
     *     
     */
    public LoginRequestBean getLoginRequest() {
        return loginRequest;
    }

    /**
     * Define el valor de la propiedad loginRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginRequestBean }
     *     
     */
    public void setLoginRequest(LoginRequestBean value) {
        this.loginRequest = value;
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
