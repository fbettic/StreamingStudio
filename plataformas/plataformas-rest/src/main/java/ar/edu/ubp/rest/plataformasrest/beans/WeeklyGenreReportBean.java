package ar.edu.ubp.rest.plataformasrest.beans;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyGenreReportBean {
    private Integer reportId;
    private Integer genreId;
    private Integer views;
}
