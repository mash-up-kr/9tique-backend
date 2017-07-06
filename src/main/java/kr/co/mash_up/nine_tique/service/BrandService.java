package kr.co.mash_up.nine_tique.service;

import java.util.List;

import kr.co.mash_up.nine_tique.vo.BrandVO;

/**
 * Brand와 관련된 비즈니스 로직 처리
 * <p>
 * Created by ethankim on 2017. 7. 3..
 */
public interface BrandService {

    /**
     * 브랜드 정보 추가
     *
     * @param brandVO
     */
    public abstract void addBrand(BrandVO brandVO);

    /**
     * 브랜드 정보 수정
     *
     * @param brandId Brand ID
     * @param brandVO
     */
    public abstract void modifyBrand(Long brandId, BrandVO brandVO);

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
    public abstract List<BrandVO> readBrands();
}
