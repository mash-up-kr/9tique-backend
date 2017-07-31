package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Seller;
import kr.co.mash_up.nine_tique.domain.SellerProduct;

/**
 * Spring JPA에서 지원하지 않는 메소드를 QueryDSL을 이용하여 구현시 이용
 */
public interface SellerRepositoryCustom {

    /**
     * 유저ID로 판매자 조회
     *
     * @param userId 유저 ID
     * @return
     */
    public abstract Optional<Seller> findOneByUserId(Long userId);

    /**
     * 판매자의 상품 리스트 조회
     *
     * @param userId 유저 ID
     * @return
     */
    public abstract List<SellerProduct> findSellerProducts(Long userId);

    /**
     * 판매자의 상품 리스트 조회
     *
     * @param userId   유저 ID
     * @param pageable
     * @return
     */
    public abstract Page<SellerProduct> findSellerProducts(Long userId, Pageable pageable);
}
