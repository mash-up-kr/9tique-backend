package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Brand;

/**
 * Created by ethankim on 2017. 7. 3..
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    /**
     * 브랜드 이름(한글)로 브랜드를 조회한다
     *
     * @param name 브랜드 이름(한글)
     * @return
     */
    public abstract Optional<Brand> findByNameKo(String name);
}
