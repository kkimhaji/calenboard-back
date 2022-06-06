package jejunu.portal.calenboard.dto;

import jejunu.portal.calenboard.entity.Board;
import jejunu.portal.calenboard.entity.User;
import lombok.*;

import java.lang.reflect.Member;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BoardDto {
    private Long bid;
    private User user;
    private String title;
    private String content;
    private LocalDate date;

//    @Builder
//    public BoardRequestDto(User user, String title, String content, Date date){
//        this.user = user;
//        this.content = content;
//        this.title = title;
//        this.date = date;
//    }

    public Board toEntity(){
        return Board.builder()
                .user(user).content(content)
                .title(title).date(date).bid(bid).build();
    }

}
