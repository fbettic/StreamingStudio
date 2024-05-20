package ar.edu.ubp.rest.anunciantesrest.beans;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisingClickReportBean {
    private Integer clickId;
    private Integer advertisingId;
    private Integer subscriberId;
    private Date clickedAt;
}
