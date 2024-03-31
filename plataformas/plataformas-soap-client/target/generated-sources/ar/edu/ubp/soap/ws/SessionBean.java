
package ar.edu.ubp.soap.ws;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para sessionBean complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="sessionBean">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="associationId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="createdAt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="expired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="filmCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="sessionUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="usedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sessionBean", propOrder = {
    "associationId",
    "createdAt",
    "expired",
    "filmCode",
    "sessionId",
    "sessionUrl",
    "usedAt"
})
public class SessionBean {

    protected Integer associationId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    protected Boolean expired;
    protected String filmCode;
    protected Integer sessionId;
    protected String sessionUrl;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar usedAt;

    /**
     * Obtiene el valor de la propiedad associationId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAssociationId() {
        return associationId;
    }

    /**
     * Define el valor de la propiedad associationId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAssociationId(Integer value) {
        this.associationId = value;
    }

    /**
     * Obtiene el valor de la propiedad createdAt.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedAt() {
        return createdAt;
    }

    /**
     * Define el valor de la propiedad createdAt.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedAt(XMLGregorianCalendar value) {
        this.createdAt = value;
    }

    /**
     * Obtiene el valor de la propiedad expired.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExpired() {
        return expired;
    }

    /**
     * Define el valor de la propiedad expired.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExpired(Boolean value) {
        this.expired = value;
    }

    /**
     * Obtiene el valor de la propiedad filmCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilmCode() {
        return filmCode;
    }

    /**
     * Define el valor de la propiedad filmCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilmCode(String value) {
        this.filmCode = value;
    }

    /**
     * Obtiene el valor de la propiedad sessionId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSessionId() {
        return sessionId;
    }

    /**
     * Define el valor de la propiedad sessionId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSessionId(Integer value) {
        this.sessionId = value;
    }

    /**
     * Obtiene el valor de la propiedad sessionUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionUrl() {
        return sessionUrl;
    }

    /**
     * Define el valor de la propiedad sessionUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionUrl(String value) {
        this.sessionUrl = value;
    }

    /**
     * Obtiene el valor de la propiedad usedAt.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUsedAt() {
        return usedAt;
    }

    /**
     * Define el valor de la propiedad usedAt.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUsedAt(XMLGregorianCalendar value) {
        this.usedAt = value;
    }

}
