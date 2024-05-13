package ar.edu.ubp.rest.plataformasrest.beans;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayRegisterBean {
    private Integer sessionId;
    private Date playedAt;
    private Integer subscriberId;
    private Integer transactionId;
    private String filmCode;
    private String sessionUrl;
}
