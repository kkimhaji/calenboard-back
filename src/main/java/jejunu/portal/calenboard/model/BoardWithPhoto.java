package jejunu.portal.calenboard.model;

import jejunu.portal.calenboard.dto.BoardDTO;
import jejunu.portal.calenboard.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Getter
@Setter
@Builder
public class BoardWithPhoto {
    private Optional<Board> board;
    private String[] photolist;
}
