
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para completeLoginAssociationRequestResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="completeLoginAssociationRequestResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="loginAssociationCompleted" type="{http://ws.soap.ubp.edu.ar/}associationRequestBean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "completeLoginAssociationRequestResponse", propOrder = {
    "loginAssociationCompleted"
})
public class CompleteLoginAssociationRequestResponse {

    protected AssociationRequestBean loginAssociationCompleted;

    /**
     * Obtiene el valor de la propiedad loginAssociationCompleted.
     * 
     * @return
     *     possible object is
     *     {@link AssociationRequestBean }
     *     
     */
    public AssociationRequestBean getLoginAssociationCompleted() {
        return loginAssociationCompleted;
    }

    /**
     * Define el valor de la propiedad loginAssociationCompleted.
     * 
     * @param value
     *     allowed object is
     *     {@link AssociationRequestBean }
     *     
     */
    public void setLoginAssociationCompleted(AssociationRequestBean value) {
        this.loginAssociationCompleted = value;
    }

}
