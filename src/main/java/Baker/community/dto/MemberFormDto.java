package Baker.community.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "닉네임을 입력 해 주세요.")
    private String name;        // 닉네임

    @NotEmpty(message = "아이디는 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해 주세요.")
    private String email;       // 아이디

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 20, message = "8~20자 사이로 입력해 주세요.")
    private String password;    // 유저 비밀번호
}
