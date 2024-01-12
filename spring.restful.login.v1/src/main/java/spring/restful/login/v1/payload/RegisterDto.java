package spring.restful.login.v1.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {
    private String name;
    private String username;
    private String password;
    private String email;
}
