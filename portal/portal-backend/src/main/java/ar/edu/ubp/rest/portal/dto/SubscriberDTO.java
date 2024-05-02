package ar.edu.ubp.rest.portal.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberDTO {
    private Integer id;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String birth;
    private List<Integer> marketingPreferences;
}
