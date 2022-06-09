package jejunu.portal.calenboard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Member member;
    @Column(nullable = false)
    private String title;

    @Builder
    public Board(String content, String title, Member member, LocalDate date, Long bid){
        this.content = content;
        this.title = title;
        this.member = member;
        this.date = date;
        this.bid= bid;
    }

}
