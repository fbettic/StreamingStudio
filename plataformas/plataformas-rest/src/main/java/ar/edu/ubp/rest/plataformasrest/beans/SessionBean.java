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
public class SessionBean {
    private Integer sessionId;
    private Integer associationId;
    private String filmCode;
    private String sessionUrl;
    private Date createdAt;
    private Date usedAt;
    private Boolean expired;
}
