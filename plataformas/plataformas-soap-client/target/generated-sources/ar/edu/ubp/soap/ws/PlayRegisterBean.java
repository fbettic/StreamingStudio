
package ar.edu.ubp.soap.ws;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para playRegisterBean complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="playRegisterBean">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="filmCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="playedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="sessionUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="subscriberId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "playRegisterBean", propOrder = {
    "filmCode",
    "playedAt",
    "sessionId",
    "sessionUrl",
    "subscriberId",
    "transactionId"
})
public class PlayRegisterBean {

    protected String filmCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar playedAt;
    protected Integer sessionId;
    protected String sessionUrl;
    protected Integer subscriberId;
    protected Integer transactionId;

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
     * Obtiene el valor de la propiedad playedAt.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPlayedAt() {
        return playedAt;
    }

    /**
     * Define el valor de la propiedad playedAt.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPlayedAt(XMLGregorianCalendar value) {
        this.playedAt = value;
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
     * Obtiene el valor de la propiedad subscriberId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSubscriberId() {
        return subscriberId;
    }

    /**
     * Define el valor de la propiedad subscriberId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSubscriberId(Integer value) {
        this.subscriberId = value;
    }

    /**
     * Obtiene el valor de la propiedad transactionId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTransactionId() {
        return transactionId;
    }

    /**
     * Define el valor de la propiedad transactionId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTransactionId(Integer value) {
        this.transactionId = value;
    }

}
