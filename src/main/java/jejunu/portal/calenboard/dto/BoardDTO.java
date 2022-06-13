package jejunu.portal.calenboard.dto;

import jejunu.portal.calenboard.entity.Board;
import jejunu.portal.calenboard.entity.Member;
import jejunu.portal.calenboard.entity.Photo;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BoardDTO {
    private Long bid;
    private String title;
    private String content;
    private String date;
//    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
//    private List<Photo> photo = new ArrayList<>();

    public Board toEntity(Member member){
        return Board.builder()
                .member(member).content(content)
                .title(title).date(date).bid(bid).build();
    }

//    public void addPhoto(Photo photo) {
//        this.photo.add(photo);
//        // 게시글에 파일이 저장되어있지 않은 경우
//        photo.setBoard(this);
//    }

}
