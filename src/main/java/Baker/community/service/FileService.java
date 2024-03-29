package Baker.community.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fildData) throws Exception {
        UUID uuid = UUID.randomUUID();                                                          // 임의의 이름
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));   //  확장자명

        String savedFileName = uuid.toString() + extension;  // 저장 될 파일이름 생성
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        // 바이트 단위의 출력을 내보내는 클래스, 생성자로 파일이 저장될 위치와 파일의 이름을 넘겨 파일 출력 스트림을 생성
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fildData);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
