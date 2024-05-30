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
public class CompleteLoginAssociationBean {
    private String email;
    private String password;
    private String uuid;

    public CompleteLoginAssociationBean(Map<String, String[]> paramMap) {

        if (paramMap.containsKey("email")) {
            this.email = paramMap.get("email")[0];
        }
        if (paramMap.containsKey("password")) {
            this.password = paramMap.get("password")[0];
        }
        if (paramMap.containsKey("uuid")) {
            this.uuid = paramMap.get("uuid")[0];
        }
    }
}
