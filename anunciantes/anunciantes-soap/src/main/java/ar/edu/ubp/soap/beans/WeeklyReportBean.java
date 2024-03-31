package ar.edu.ubp.soap.beans;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyReportBean {
    private int reportId;
    private int serviceId;
    private int totalClicks;
    private Date fromDate;
    private Date toDate;
}
