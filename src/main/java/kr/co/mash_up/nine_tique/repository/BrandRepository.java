package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Brand;

/**
 * Created by ethankim on 2017. 7. 3..
 */
@Repository(value = "brandRepository")
public interface BrandRepository extends JpaRepository<Brand, Long> {

    public abstract Optional<Brand> findByNameKo(String name);
}
