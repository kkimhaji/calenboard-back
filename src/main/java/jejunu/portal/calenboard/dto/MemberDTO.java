package jejunu.portal.calenboard.dto;

import jejunu.portal.calenboard.entity.Member;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private Long uid;
    private String email;
    private String nickname;
    private String password;

    public Member toEntity(){
        return Member.builder()
                .uid(uid).email(email).password(password).nickname(nickname).build();
    }

}
