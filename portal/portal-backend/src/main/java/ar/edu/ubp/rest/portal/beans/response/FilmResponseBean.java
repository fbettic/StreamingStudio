package ar.edu.ubp.rest.portal.beans.response;

import org.w3c.dom.Element;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class FilmResponseBean extends ServiceResponseBean{
    private String actors;
    private String countryCode;
    private String cover;
    private String description;
    private String director;
    private String filmCode;
    private String genres;
    private Boolean highlighted;
    private Boolean newContent;
    private String title;
    private Integer year;

    public FilmResponseBean(Element filmElement) {
        this.actors = getElementValue(filmElement, "actors");
        this.countryCode = getElementValue(filmElement, "countryCode");
        this.cover = getElementValue(filmElement, "cover");
        this.description = getElementValue(filmElement, "description");
        this.director = getElementValue(filmElement, "director");
        this.filmCode = getElementValue(filmElement, "filmCode");
        this.genres = getElementValue(filmElement, "genres");
        this.highlighted = Boolean.parseBoolean(getElementValue(filmElement, "highlighted"));
        this.newContent = Boolean.parseBoolean(getElementValue(filmElement, "newContent"));
        this.title = getElementValue(filmElement, "title");
        this.year = Integer.parseInt(getElementValue(filmElement, "year"));
        
    }

}
