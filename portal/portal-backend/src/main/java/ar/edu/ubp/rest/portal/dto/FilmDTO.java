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
    Integer filmid;
    String filmCode;
    String title;
    String cover;
    String description;
    String directorName;
    String countryCode;
    Integer year;
    String actorNames;
    String genreNames;}
