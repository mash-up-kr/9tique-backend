package kr.co.mash_up.nine_tique.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.config.SystemPropertiesConfig;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    public static File upload(MultipartFile multipartFile, String uploadDir, String fileName) {
        File dirPath = new File(uploadDir);
        if (!dirPath.exists()) {
            boolean made = dirPath.mkdirs();  // 해당 경로에 없는 dir 모두 생성
            if (!made) {
                throw new RuntimeException(String.format("make directory(%s) fail", uploadDir));
            }
        }

        String targetFilePath = uploadDir + "/" + fileName;
        File targetFile = new File(targetFilePath);

        try {
            // file save 방법1
            multipartFile.transferTo(targetFile);

            // file save 방법2
//            byte[] bytes = multipartFile.getBytes();
//            BufferedOutputStream bufferStream =
//                    new BufferedOutputStream(new FileOutputStream(targetFile));
//            bufferStream.write(bytes);
//            bufferStream.close();

        } catch (IOException e) {
            throw new RuntimeException(String.format("file upload error: %s", targetFile), e);
        }
        return targetFile;
    }

//    /**
//     * 업로드된 이미지를 다운받을 수 있는 url 제공
//     * @return image url
//     */
//    public static String getImageUrl(long productId, String originalFileName){
//        return String.format("$s/product/%d/%s",
//                System.getProperty(SystemPropertiesConfig.STORAGE_URI), productId, originalFileName);
//    }
//
//    /**
//     * 물리적인 image upload path 제공
//     * @return upload path
//     */
//    public static String getImageUploadPath(long productId){
//        return String.format("%s/product/%d", System.getProperty(SystemPropertiesConfig.STORAGE_PATH), productId);
//    }
}
