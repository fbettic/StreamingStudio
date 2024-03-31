package ar.edu.ubp.rest.plataformasrest.beans;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyReportBean {
    private Integer reportId;
    private Integer serviceId;
    private Integer signupQuantity;
    private Integer associationsQuantity;
    private Date fromDate;
    private Date toDate;
}
