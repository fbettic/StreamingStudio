
package ar.edu.ubp.soap.ws;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para associationRequestBean complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="associationRequestBean">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="associationId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="associationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="authUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="canceledAt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="redirectUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="requestedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="serviceId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="userId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="userToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "associationRequestBean", propOrder = {
    "associationId",
    "associationType",
    "authUrl",
    "canceledAt",
    "redirectUrl",
    "requestedAt",
    "serviceId",
    "state",
    "userId",
    "userToken"
})
public class AssociationRequestBean {

    protected Integer associationId;
    protected String associationType;
    protected String authUrl;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar canceledAt;
    protected String redirectUrl;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestedAt;
    protected Integer serviceId;
    protected String state;
    protected Integer userId;
    protected String userToken;

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
     * Obtiene el valor de la propiedad associationType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssociationType() {
        return associationType;
    }

    /**
     * Define el valor de la propiedad associationType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssociationType(String value) {
        this.associationType = value;
    }

    /**
     * Obtiene el valor de la propiedad authUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthUrl() {
        return authUrl;
    }

    /**
     * Define el valor de la propiedad authUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthUrl(String value) {
        this.authUrl = value;
    }

    /**
     * Obtiene el valor de la propiedad canceledAt.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCanceledAt() {
        return canceledAt;
    }

    /**
     * Define el valor de la propiedad canceledAt.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCanceledAt(XMLGregorianCalendar value) {
        this.canceledAt = value;
    }

    /**
     * Obtiene el valor de la propiedad redirectUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Define el valor de la propiedad redirectUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedirectUrl(String value) {
        this.redirectUrl = value;
    }

    /**
     * Obtiene el valor de la propiedad requestedAt.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestedAt() {
        return requestedAt;
    }

    /**
     * Define el valor de la propiedad requestedAt.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestedAt(XMLGregorianCalendar value) {
        this.requestedAt = value;
    }

    /**
     * Obtiene el valor de la propiedad serviceId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getServiceId() {
        return serviceId;
    }

    /**
     * Define el valor de la propiedad serviceId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setServiceId(Integer value) {
        this.serviceId = value;
    }

    /**
     * Obtiene el valor de la propiedad state.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Define el valor de la propiedad state.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Obtiene el valor de la propiedad userId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Define el valor de la propiedad userId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUserId(Integer value) {
        this.userId = value;
    }

    /**
     * Obtiene el valor de la propiedad userToken.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * Define el valor de la propiedad userToken.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserToken(String value) {
        this.userToken = value;
    }

}
