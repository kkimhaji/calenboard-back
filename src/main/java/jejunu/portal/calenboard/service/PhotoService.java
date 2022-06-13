package jejunu.portal.calenboard.service;

import jejunu.portal.calenboard.dto.PhotoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoService {

    @Value("${org.portal.upload.path}")
    private String uploadPath;

    public void uploadFile(MultipartFile[] uploadFiles, Long uid, String date){
        for(MultipartFile uploadFile: uploadFiles){
            if(!uploadFile.getContentType().startsWith("image")){
                break;
            }

            if(ObjectUtils.isEmpty(uploadFile.getContentType())) break;

            String folderPath = makeFolder(uid.toString()+"/"+date.toString());
            System.out.println("folderPath: " + folderPath);
            String originName = uploadFile.getOriginalFilename();
            String saveName = UUID.randomUUID().toString().replaceAll("-", "")+originName;
            PhotoDTO photoDTO = PhotoDTO.builder().originName(originName)
                                .filePath(saveName).fileSize(uploadFile.getSize()).build();

            Path savePath = Paths.get(saveName);
            File saveFile = new File(folderPath, saveName);
            System.out.println(savePath.toString());
            System.out.println(saveFile.toString());
            try{
                uploadFile.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String makeFolder(String path) {

        File uploadPathFolder = new File(uploadPath, path);
        if(!uploadPathFolder.exists()) uploadPathFolder.mkdirs();
        return path;
    }

}
