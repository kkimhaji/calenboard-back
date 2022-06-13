package jejunu.portal.calenboard.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class BoardFileDTO {
    private String memberId;
    private String title;
    private String content;
    private LocalDate date;
    private List<MultipartFile> files;
}
