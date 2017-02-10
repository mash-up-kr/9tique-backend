package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring JPA에서 지원하지 않는 메소드를 QueryDSL을 이용하여 구현시 이용
 */
public interface ShopRepositoryCustom {
    Shop findByUserId(Long userId);

    Page<Shop> findShops(Pageable pageable);
}
