package kr.co.mash_up.nine_tique.service;

import org.springframework.data.domain.Page;

import java.util.List;

import kr.co.mash_up.nine_tique.web.dto.BrandDto;
import kr.co.mash_up.nine_tique.web.dto.ProductDto;
import kr.co.mash_up.nine_tique.web.vo.BrandRequestVO;
import kr.co.mash_up.nine_tique.web.vo.ProductListRequestVO;

/**
 * Brand와 관련된 비즈니스 로직 처리
 * <p>
 * Created by ethankim on 2017. 7. 3..
 */
public interface BrandService {

    /**
     * 브랜드 정보 추가
     *
     * @param brandVO 추가할 브랜드 정보
     */
    public abstract void addBrand(BrandRequestVO brandVO);

    /**
     * 브랜드 정보 수정
     *
     * @param brandId Brand ID
     * @param brandVO 수정할 브랜드 정보
     */
    public abstract void modifyBrand(Long brandId, BrandRequestVO brandVO);

    /**
     * 브래드 정보 삭제
     *
     * @param brandId Brand ID
     */
    public abstract void removeBrand(Long brandId);

    /**
     * 브랜드 리스트 조회
     *
     * @return 브랜드 리스트
     */
    public abstract List<BrandDto> readBrands();

    /**
     * 브랜드 상품 리스트 조회
     *
     * @param brandId   브랜드 ID
     * @param userId    User ID
     * @param requestVO 카테고리 및 페이징 정보
     * @return
     */
    public abstract Page<ProductDto> readBrandProducts(Long brandId, Long userId, ProductListRequestVO requestVO);
}
