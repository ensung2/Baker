package Baker.community.dto;

import Baker.community.constant.Role;
import lombok.Data;

@Data
public class JoinRequest {

    private final String name;
    private final String email;
    private final String password;
    private final Role role;


}
