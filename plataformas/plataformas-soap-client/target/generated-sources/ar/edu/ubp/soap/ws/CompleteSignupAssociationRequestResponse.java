
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para completeSignupAssociationRequestResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="completeSignupAssociationRequestResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="signupAssociationCompleted" type="{http://ws.soap.ubp.edu.ar/}associationRequestBean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "completeSignupAssociationRequestResponse", propOrder = {
    "signupAssociationCompleted"
})
public class CompleteSignupAssociationRequestResponse {

    protected AssociationRequestBean signupAssociationCompleted;

    /**
     * Obtiene el valor de la propiedad signupAssociationCompleted.
     * 
     * @return
     *     possible object is
     *     {@link AssociationRequestBean }
     *     
     */
    public AssociationRequestBean getSignupAssociationCompleted() {
        return signupAssociationCompleted;
    }

    /**
     * Define el valor de la propiedad signupAssociationCompleted.
     * 
     * @param value
     *     allowed object is
     *     {@link AssociationRequestBean }
     *     
     */
    public void setSignupAssociationCompleted(AssociationRequestBean value) {
        this.signupAssociationCompleted = value;
    }

}
