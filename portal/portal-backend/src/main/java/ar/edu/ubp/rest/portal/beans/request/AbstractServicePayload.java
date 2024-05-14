package ar.edu.ubp.rest.portal.beans.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractServicePayload {
    
    protected String authToken;

    public abstract String toSoapXml();
}
