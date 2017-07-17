package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.mash_up.nine_tique.domain.Image;

@Repository(value = "imageRepository")
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * 파일 이름으로 이미지 조회
     *
     * @param fileName
     * @return
     */
    public abstract Image findByFileName(String fileName);
}
