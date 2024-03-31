
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para createAssociationRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="createAssociationRequest">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="newAssociationRequest" type="{http://ws.soap.ubp.edu.ar/}newAssociationRequestBean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createAssociationRequest", propOrder = {
    "newAssociationRequest"
})
public class CreateAssociationRequest {

    protected NewAssociationRequestBean newAssociationRequest;

    /**
     * Obtiene el valor de la propiedad newAssociationRequest.
     * 
     * @return
     *     possible object is
     *     {@link NewAssociationRequestBean }
     *     
     */
    public NewAssociationRequestBean getNewAssociationRequest() {
        return newAssociationRequest;
    }

    /**
     * Define el valor de la propiedad newAssociationRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link NewAssociationRequestBean }
     *     
     */
    public void setNewAssociationRequest(NewAssociationRequestBean value) {
        this.newAssociationRequest = value;
    }

}
