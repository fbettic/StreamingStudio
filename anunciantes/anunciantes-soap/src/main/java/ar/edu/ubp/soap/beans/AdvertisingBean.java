package ar.edu.ubp.soap.beans;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisingBean {
    private Integer advertisingId;
    private Integer bannerId;
    private String text;
    private String imageUrl;
    private String redirectUrl;
    private Integer serviceId;
    private String priorityTitle;
    private Date fromDate;
    private Date toDate;   
}
