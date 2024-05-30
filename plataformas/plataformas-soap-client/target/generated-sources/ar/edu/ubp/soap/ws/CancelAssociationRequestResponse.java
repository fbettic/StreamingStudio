
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cancelAssociationRequestResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="cancelAssociationRequestResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="associationRequest" type="{http://ws.soap.ubp.edu.ar/}associationRequestBean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cancelAssociationRequestResponse", propOrder = {
    "associationRequest"
})
public class CancelAssociationRequestResponse {

    protected AssociationRequestBean associationRequest;

    /**
     * Obtiene el valor de la propiedad associationRequest.
     * 
     * @return
     *     possible object is
     *     {@link AssociationRequestBean }
     *     
     */
    public AssociationRequestBean getAssociationRequest() {
        return associationRequest;
    }

    /**
     * Define el valor de la propiedad associationRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link AssociationRequestBean }
     *     
     */
    public void setAssociationRequest(AssociationRequestBean value) {
        this.associationRequest = value;
    }

}
