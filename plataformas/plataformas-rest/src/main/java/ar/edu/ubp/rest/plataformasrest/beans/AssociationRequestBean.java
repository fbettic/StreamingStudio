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
public class AssociationRequestBean {
    private Integer associationId;
    private Integer userId;
    private Integer serviceId;
    private String associationType;
    private String state;
    private String authUrl;
    private String redirectUrl;
    private Date requestedAt;
    private String userToken;
    private Date canceledAt;
}
