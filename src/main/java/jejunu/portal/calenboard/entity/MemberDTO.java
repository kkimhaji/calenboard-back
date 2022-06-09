package jejunu.portal.calenboard.entity;

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
