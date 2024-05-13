package ar.edu.ubp.soap.beans;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisingClickReportBean {
    private Integer clickId;
    private Integer bannerId;
    private Integer subscriberId;
    private Date clickedAt;
}