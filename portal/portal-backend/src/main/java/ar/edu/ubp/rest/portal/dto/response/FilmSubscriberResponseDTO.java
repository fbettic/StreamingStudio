package ar.edu.ubp.rest.portal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmSubscriberResponseDTO {
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
    String platforms;
    String higligtedBy;
    String newOn;
}
