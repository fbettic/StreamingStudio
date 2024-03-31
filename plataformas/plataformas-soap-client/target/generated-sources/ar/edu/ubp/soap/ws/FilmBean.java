
package ar.edu.ubp.soap.ws;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para filmBean complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="filmBean">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="actors" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="countryCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="cover" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="director" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="filmCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="filmId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         <element name="genres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="highlighted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="newContent" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="year" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "filmBean", propOrder = {
    "actors",
    "countryCode",
    "cover",
    "description",
    "director",
    "filmCode",
    "filmId",
    "genres",
    "highlighted",
    "newContent",
    "title",
    "year"
})
public class FilmBean {

    protected String actors;
    protected String countryCode;
    protected String cover;
    protected String description;
    protected String director;
    protected String filmCode;
    protected Integer filmId;
    protected String genres;
    protected Boolean highlighted;
    protected Boolean newContent;
    protected String title;
    protected Integer year;

    /**
     * Obtiene el valor de la propiedad actors.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActors() {
        return actors;
    }

    /**
     * Define el valor de la propiedad actors.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActors(String value) {
        this.actors = value;
    }

    /**
     * Obtiene el valor de la propiedad countryCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Define el valor de la propiedad countryCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Obtiene el valor de la propiedad cover.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCover() {
        return cover;
    }

    /**
     * Define el valor de la propiedad cover.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCover(String value) {
        this.cover = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad director.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirector() {
        return director;
    }

    /**
     * Define el valor de la propiedad director.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirector(String value) {
        this.director = value;
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
     * Obtiene el valor de la propiedad filmId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFilmId() {
        return filmId;
    }

    /**
     * Define el valor de la propiedad filmId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFilmId(Integer value) {
        this.filmId = value;
    }

    /**
     * Obtiene el valor de la propiedad genres.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenres() {
        return genres;
    }

    /**
     * Define el valor de la propiedad genres.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenres(String value) {
        this.genres = value;
    }

    /**
     * Obtiene el valor de la propiedad highlighted.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHighlighted() {
        return highlighted;
    }

    /**
     * Define el valor de la propiedad highlighted.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHighlighted(Boolean value) {
        this.highlighted = value;
    }

    /**
     * Obtiene el valor de la propiedad newContent.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNewContent() {
        return newContent;
    }

    /**
     * Define el valor de la propiedad newContent.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNewContent(Boolean value) {
        this.newContent = value;
    }

    /**
     * Obtiene el valor de la propiedad title.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Define el valor de la propiedad title.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Obtiene el valor de la propiedad year.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Define el valor de la propiedad year.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setYear(Integer value) {
        this.year = value;
    }

}
