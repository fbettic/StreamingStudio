package ar.edu.ubp.rest.anunciantesrest.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyAdvertisingReportBean {
    private int reportId;
    private int advertisingId;
    private int clicks;
}
