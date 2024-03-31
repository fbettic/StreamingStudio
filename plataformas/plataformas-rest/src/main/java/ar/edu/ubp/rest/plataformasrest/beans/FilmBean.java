package ar.edu.ubp.rest.plataformasrest.beans;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmBean {
    private Integer filmId;
    private String filmCode;
    private String title;
    private String cover;
    private String description;
    private String director;
    private String countryCode;
    private Integer year;
    private String actors;
    private String genres;
    private Boolean newContent;
    private Boolean highlighted;
}
