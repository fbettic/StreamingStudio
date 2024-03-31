package beans;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyFilmReportBean {
    private Integer reportId;
    private Integer filmId;
    private Integer views;
}
