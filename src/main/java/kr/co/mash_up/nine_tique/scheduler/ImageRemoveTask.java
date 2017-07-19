package kr.co.mash_up.nine_tique.scheduler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.mash_up.nine_tique.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 매일 23시 59분에 임시폴더에(/tmp) 있는 파일을 삭제하는 스케쥴러
 */
@Component
@Slf4j
public class ImageRemoveTask {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Async  // 동시에 여러 스케쥴러가 실행되도 실행가능하게 하기 위함
    @Scheduled(cron = "0 59 23 * * ?")  // 매일 23시 59분에 실행
    public void work() {
        StopWatch stopWatch = new StopWatch(sdf.format(new Date()));
        stopWatch.setKeepTaskList(true);

        stopWatch.start("start file remove /tmp...");

        // /tmp에 있는 파일을 삭제한다.
        boolean result = FileUtil.deleteFilesInDir(FileUtil.getImageUploadTempPath());

        stopWatch.stop();
        log.info("end file remove /tmp !! result : {}, {}", result, stopWatch.toString());
    }
}
