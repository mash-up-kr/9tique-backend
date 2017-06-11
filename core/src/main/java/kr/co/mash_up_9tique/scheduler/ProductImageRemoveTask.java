package kr.co.mash_up_9tique.scheduler;

import kr.co.mash_up_9tique.domain.ProductImage;
import kr.co.mash_up_9tique.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 매일 23시 59분에 임시폴더에 있는 상품 이미지를 삭제하는 스케쥴러
 */
@Component
@Slf4j
public class ProductImageRemoveTask {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Async  // 동시에 여러 스케쥴러가 실행되도 실행가능하게 하기 위함
    @Scheduled(cron = "0 59 23 * * ?")  // 매일 23시 59분에 실행
    public void work() {
        log.info(sdf.format(new Date()) + " file remove /tmp start!");

        // /storage/product/tmp에 있는 파일을 삭제한다.
        boolean result = FileUtil.deleteFilesInDir(ProductImage.getImageUploadTempPath());
        log.info(sdf.format(new Date()) + " file remove /tmp end!!" + result);
    }
}