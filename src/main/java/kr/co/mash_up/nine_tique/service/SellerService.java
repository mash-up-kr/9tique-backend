package kr.co.mash_up.nine_tique.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.dto.SellerDto;
import kr.co.mash_up.nine_tique.dto.UserDto;
import kr.co.mash_up.nine_tique.vo.ProductDeleteRequestVO;
import kr.co.mash_up.nine_tique.vo.SellerRequestVO;

/**
 * Seller와 관련된 비즈니스 로직 처리를 담당한다
 * <p>
 * Created by ethankim on 2017. 7. 29..
 */
public interface SellerService {

    /**
     * 판매자가 등록한 상품 리스트 조회
     *
     * @param userId   Seller ID
     * @param pageable page 정보
     * @return 판매자가 등록한 상품 리스트
     */
    public abstract Page<ProductDto> readProducts(Long userId, Pageable pageable);

    /**
     * 판매자가 등록한 상품 전체삭제
     *
     * @param userId Seller ID
     */
    public abstract void removeProductsAll(Long userId);

    /**
     * 판매자가 등록한 상품 삭제
     *
     * @param userId    Seller ID
     * @param requestVO 삭제할 Product ID들
     */
    public abstract void removeProducts(Long userId, ProductDeleteRequestVO requestVO);

    /**
     * Seller의 정보 조회
     * shop, name 등
     *
     * @param userId 조회할 유저 ID
     * @return 조회한 정보
     */
    public abstract SellerDto findSellerInfo(Long userId);

    /**
     * 유저를 판매자로 등록한다
     *
     * @param userId       유저 ID
     * @param authentiCode 인증코드
     * @return 생성된 access token
     */
    public abstract UserDto registerSeller(Long userId, String authentiCode);

    /**
     * 매장에 판매자 추가 -> 인증코드 발급
     *
     * @param shopId Shop ID
     */
    public abstract void addSeller(Long shopId);

    /**
     * 판매자 정보 수정
     *
     * @param userId    Seller ID
     * @param requestVO 수정할 판매자 정보(shop, user name 등)
     */
    public abstract void modifySeller(Long userId, SellerRequestVO requestVO);

    /**
     * 판매자 리스트 조회
     *
     * @param pageable
     * @return
     */
    public abstract Page<SellerDto> readSellers(Pageable pageable);
}
