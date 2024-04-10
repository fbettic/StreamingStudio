package ar.edu.ubp.rest.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformFilmDTO {
    private Integer platformId;
    private String filmCode;
    private Boolean newContent;
    private Boolean highlighted;
}
