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
public class SessionDTO {
    private Integer sessionId;
    private Date usedAt;
    private Integer subscriberId;
    private Integer platformId;
    private Integer transactionId;
    private Integer filmid;
    private String filmCode;
    private String sessionUrl;
    private Date createdAt;
}
