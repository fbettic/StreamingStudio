package ar.edu.ubp.soap.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceConnectionBean {
    private int serviceId;
    private String name;
    private String authToken;

    
}

