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
public class AssociationDTO {
    private Integer transactionId;
    private Integer platformId;
    private Integer subscriberId;
    private String userToken;
    private Date entryDate;
    private Date leavingDate;
}
