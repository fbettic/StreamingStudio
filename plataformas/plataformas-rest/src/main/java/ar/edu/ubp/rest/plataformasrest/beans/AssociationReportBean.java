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
public class AssociationReportBean {
    private Integer subscriberId;
    private Integer transactionId;
    private String associationType;
    private String authUrl;
    private Date entryDate;
    private Date leavingDate;
}
