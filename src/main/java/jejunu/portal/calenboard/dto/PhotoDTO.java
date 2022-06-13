package jejunu.portal.calenboard.dto;

import jejunu.portal.calenboard.entity.Board;
import jejunu.portal.calenboard.entity.Photo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PhotoDTO {
    private Board board;
    private String originName;
    private String filePath;
    private Long fileSize;

    public Photo toEntity(Board board){
        return Photo.builder().originName(originName)
                    .filePath(filePath).fileSize(fileSize).build();

    }

}
