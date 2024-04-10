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
public class AssociationRequestDTO {
    private Integer platformId;
    private Integer transactionId;
    private Integer subscriberId;
    private String state;
    private String uuid;
    private String authUrl;
    private String associationType;
    private Date requestAt;
    private Date closedAt ;
}
