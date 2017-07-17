package kr.co.mash_up.nine_tique.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import kr.co.mash_up.nine_tique.dto.ImageDto;

/**
 * Image와 관련된 비즈니스 로직 처리
 * <p>
 * Created by ethankim on 2017. 7. 15..
 */
public interface ImageService {

    /**
     * 이미지 파일 추가
     *
     * @param files
     * @return
     */
    public abstract List<ImageDto> addImages(List<MultipartFile> files);
}
