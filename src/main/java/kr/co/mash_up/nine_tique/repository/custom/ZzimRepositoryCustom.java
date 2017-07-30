package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.ZzimProduct;

/**
 * Spring JPA에서 지원하지 않는 메소드를 QueryDSL을 이용하여 구현시 이용
 */
public interface ZzimRepositoryCustom {

    /**
     * 유저가 찜한 상품을 조회한다
     *
     * @param userId    유저 ID
     * @param productId 상품 ID
     * @return
     */
    public abstract Optional<ZzimProduct> getZzimProduct(Long userId, Long productId);

    /**
     * 유저 ID로 유저가 찜한 상품 리스트를 조회한다
     *
     * @param userId   유저 ID
     * @param pageable
     * @return
     */
    public abstract Page<ZzimProduct> getZzimProducts(Long userId, Pageable pageable);

    /**
     * 유저 ID로 유저가 찜한 상품 리스트를 조회한다
     *
     * @param userId 유저 ID
     * @return
     */
    public abstract List<ZzimProduct> getZzimProducts(Long userId);
}
