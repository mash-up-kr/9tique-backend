package kr.co.mash_up.nine_tique.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public static boolean moveFile(String source, String dest){
        Path file = Paths.get(source);
        Path movePath = Paths.get(dest);

        boolean movoPathExists = Files.exists(movePath,
                LinkOption.NOFOLLOW_LINKS);

        if(!movoPathExists){  // 디렉토리 없으면 생성
            try {
                Files.createDirectories(movePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.move(file, movePath.resolve(file.getFileName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean deleteFile(String imageFilePath) {
        File imageFile = new File(imageFilePath);
        if (!imageFile.exists()) {
            return false;
        }
        return imageFile.delete();
    }

    public static boolean deleteFilesInDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            return false;
        }

        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            }
        }
        return true;
    }

    public static boolean deleteDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            return false;
        }

        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                deleteDir(file.getPath());
            }
        }
        return dir.delete();
    }

//    /**
//     * 업로드된 이미지를 다운받을 수 있는 url 제공
//     * @return image url
//     */
//    public static String getImageUrl(long productId, String fileName){
//        return String.format("$s/product/%d/%s",
//                System.getProperty(SystemPropertiesConfig.STORAGE_URI), productId, fileName);
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
