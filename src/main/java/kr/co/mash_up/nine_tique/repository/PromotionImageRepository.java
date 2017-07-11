package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.PromotionImage;

/**
 * Created by ethankim on 2017. 7. 10..
 */
@Repository
public interface PromotionImageRepository extends JpaRepository<PromotionImage, Long> {

    /**
     * 파일 이름으로 PromotionImage를 조회한다
     *
     * @param fileName 파일 이름
     * @return
     */
    public abstract Optional<PromotionImage> findByFileName(String fileName);
}
