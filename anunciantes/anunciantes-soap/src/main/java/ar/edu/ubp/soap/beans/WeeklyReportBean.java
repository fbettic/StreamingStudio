package ar.edu.ubp.soap.beans;


import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyReportBean {
    private String authToken;
    private Integer reportId;
    private Date fromDate;
    private Date toDate;
    private List<AdvertisingClickReportBean> clicks;
}
