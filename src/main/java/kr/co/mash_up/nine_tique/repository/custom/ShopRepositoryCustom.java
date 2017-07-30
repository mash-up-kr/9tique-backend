package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Shop;

/**
 * Spring JPA에서 지원하지 않는 메소드를 QueryDSL을 이용하여 구현시 이용
 */
public interface ShopRepositoryCustom {

    /**
     * Shop 단건 조회
     *
     * @param shopId Shop ID
     * @return
     */
    public abstract Optional<Shop> findOneByShopId(Long shopId);

    /**
     * 판매자인 User ID로 shop 단건 조회
     *
     * @param userId 판매자인 User의 ID
     * @return
     */
    public abstract Optional<Shop> findOneByUserId(Long userId);

    /**
     * 매장 리스트 조회
     *
     * @param pageable
     * @return
     */
    public abstract Page<Shop> findShops(Pageable pageable);
}
