package ar.edu.ubp.rest.anunciantesrest.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyPriorityReportBean {
    private int reportId;
    private int priorityId;
    private int clicks;
}
