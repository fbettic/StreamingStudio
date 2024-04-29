package ar.edu.ubp.rest.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDTO {
    Integer filmId;
    String filmCode;
    String title;
    String cover;
    String description;
    String director;
    String countryCode;
    Integer year;
    String actors;
    String genres;
}
