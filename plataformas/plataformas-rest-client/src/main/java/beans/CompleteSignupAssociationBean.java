package beans;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteSignupAssociationBean {
    private String uuid;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;

    public CompleteSignupAssociationBean(Map<String, String[]> paramMap) {

        if (paramMap.containsKey("firstname")) {
            this.firstname = paramMap.get("firstname")[0];
        }
        if (paramMap.containsKey("lastname")) {
            this.lastname = paramMap.get("lastname")[0];
        }
        if (paramMap.containsKey("email")) {
            this.email = paramMap.get("email")[0];
        }
        if (paramMap.containsKey("password")) {
            this.password = paramMap.get("password")[0];
        }
        if (paramMap.containsKey("phone")) {
            this.phone = paramMap.get("phone")[0];
        }
        if (paramMap.containsKey("uuid")) {
            this.uuid = paramMap.get("uuid")[0];
        }
    }
}
