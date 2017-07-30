package kr.co.mash_up.nine_tique.service;

import org.springframework.data.domain.Page;

import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.vo.ProductListRequestVO;
import kr.co.mash_up.nine_tique.vo.ProductRequestVO;

/**
 * Product와 관련된 비즈니스 로직 처리
 * <p>
 * Created by ethankim on 2017. 7. 29..
 */
public interface ProductService {

    /**
     * 상품 추가
     *
     * @param userId    Seller ID
     * @param requestVO 추가할 상품 정보
     */
    public abstract void addProduct(Long userId /* seller id */, ProductRequestVO requestVO);

    /**
     * 상품 수정
     *
     * @param userId    상품을 동록한 Seller ID
     * @param productId 상품 ID
     * @param requestVO 수정할 상품 정보return
     */
    public abstract void modifyProduct(Long userId, Long productId, ProductRequestVO requestVO);

    /**
     * 상품 상태 수정(판매중/완료)
     *
     * @param userId    상품을 등록한 Seller ID
     * @param productId 상품 ID
     * @param status    수정할 상품 상태return
     */
    public abstract void modifyProductStatus(Long userId, Long productId, Product.Status status);

    /**
     * 상품 삭제
     *
     * @param userId    상품을 등록한 Seller ID
     * @param productId 상품 ID
     */
    public abstract void removeProduct(Long userId, Long productId);

    /**
     * 카테고리별 상품 리스트 조회
     *
     * @param userId    유저 ID(찜, 내가 등록한 상품인지 확인)
     * @param requestVO 페이징 정보
     * @return
     */
    public abstract Page<ProductDto> readProductsByCategory(Long userId, ProductListRequestVO requestVO);

    /**
     * 상품 상세정보 조회
     *
     * @param userId    유저 ID(찜, 내가 등록한 상품인지 확인)
     * @param productid 상품 ID
     * @return
     */
    public abstract ProductDto readProduct(Long userId, Long productid);
}
