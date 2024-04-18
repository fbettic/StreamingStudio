package ar.edu.ubp.rest.portal.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisingDTO {
    private Integer advertisingId;
    private Integer advertiserId;
    private Integer sizeId;
    private String sizeType;
    private Integer sizeValue;
    private Float sizeFee;
    private Integer allPagesFeeId; 
    private Float allPagesFee;
    private Integer priorityId;
    private String priorityType;
    private Integer priorityValue;
    private Float priorityFee;
    private String redirectUrl;
    private String imageUrl;
    private String bannerText;
    private Integer bannerId;
    private Date fromDate;
    private Date toDate;
}