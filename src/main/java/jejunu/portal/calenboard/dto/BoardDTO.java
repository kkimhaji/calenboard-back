package jejunu.portal.calenboard.dto;

import jejunu.portal.calenboard.entity.Board;
import jejunu.portal.calenboard.entity.Member;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BoardDTO {
    private Long bid;
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

    public Board toEntity(Member member){
        return Board.builder()
                .member(member).content(content)
                .title(title).date(date).bid(bid).build();
    }

}
