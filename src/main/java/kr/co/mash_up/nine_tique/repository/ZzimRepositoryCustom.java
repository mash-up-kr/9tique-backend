package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.ZzimProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Spring JPA에서 지원하지 않는 메소드를 QueryDSL을 이용하여 구현시 이용
 */
public interface ZzimRepositoryCustom {

    Page<ZzimProduct> getZzimProducts(Long userId, Pageable pageable);

    List<ZzimProduct> getZzimProducts(Long userId);
}
