//package jejunu.portal.calenboard.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
//@Entity
//@Table(name = "file")
//public class Photo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="file_id")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "board_id")
//    private Board board;
//
//    private String originName;//원본명
//
//    private String filePath;
//    private Long fileSize;
//
//    @Builder
//    public Photo(String originName, String filePath, Long fileSize){
//        this.originName = originName;
//        this.filePath = filePath;
//        this.fileSize = fileSize;
//    }
//
//
//}
