package jejunu.portal.calenboard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
    @Column(nullable = false)
    private User user;
    private String title;

    @Builder
    public Board(String content, String title, User user, LocalDate date, Long bid){
        this.content = content;
        this.title = title;
        this.user = user;
        this.date = date;
        this.bid= bid;
    }

}
