package ar.edu.ubp.rest.plataformasrest.beans;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewAssociationRequestBean {
    private String associationType;
    private String redirectUrl;
    private String authToken;
}
