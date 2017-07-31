package kr.co.mash_up.nine_tique.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.mash_up.nine_tique.web.dto.ProductDto;

/**
 * Zzim과 관련된 비즈니스 로직 처리
 * <p>
 * Created by ethankim on 2017. 7. 28..
 */
public interface ZzimService {

    /**
     * 찜하기
     *
     * @param userId    요청한 유저 ID
     * @param productId 찜할 상품 ID
     * @return 성공/실패 여부
     */
    public abstract void addZzimProduct(Long userId, Long productId);

    /**
     * 찜해제
     *
     * @param userId    요청한 유저 ID
     * @param productId 찜 해제할 상품 ID
     * @return 성공/실패 여부
     */
    public abstract void removeZzimProduct(Long userId, Long productId);

    /**
     * 찜 목록 조회
     *
     * @param userId   요청한 유저 ID
     * @param pageable page 정보
     * @return 목록
     */
    public abstract Page<ProductDto> readZzimProducts(Long userId, Pageable pageable);
}
