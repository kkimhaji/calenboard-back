package jejunu.portal.calenboard.service;

import jdk.jshell.spi.ExecutionControlProvider;
import jejunu.portal.calenboard.dto.PhotoDTO;
import jejunu.portal.calenboard.entity.Photo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class FileHandler {

    private final PhotoService photoService;

    public List<Photo> parseFileInfo(
            List<MultipartFile> multipartFiles
    ) throws Exception{
        List<Photo> fileList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(multipartFiles)){
            //프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
            //파일 저장 세부 경로 지정
            String path = "images" + UUID.randomUUID().toString()+ File.separator;
            File file = new File(path);

            if(!file.exists()){ //directory 존재 x
                boolean wasSuccessful = file.mkdirs();
                if(!wasSuccessful) System.out.println("file: was not successful");

            }
            for(MultipartFile multipartFile: multipartFiles){
                String originFileEx;
                String contentType = multipartFile.getContentType();

                if(ObjectUtils.isEmpty(contentType)) break;
                else{
                    if(contentType.contains("image/jpeg")) originFileEx = ".jpg";
                    else if(contentType.contains("image/png")) originFileEx = ".png";
                    else break;
                }

                String newFilename = UUID.randomUUID().toString() + originFileEx;

                PhotoDTO photoDTO = PhotoDTO.builder().originName(multipartFile.getOriginalFilename())
                                                    .filePath(path + File.separator + newFilename)
                                                    .fileSize(multipartFile.getSize()).build();


                Photo photo = new Photo(photoDTO.getOriginName(), photoDTO.getFilePath(), photoDTO.getFileSize());

                fileList.add(photo);

                file = new File(absolutePath + path + File.separator + newFilename);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }
        }
        return fileList;
    }

}
