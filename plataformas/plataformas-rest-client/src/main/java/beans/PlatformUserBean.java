package beans;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformUserBean {
    private Integer userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
}
