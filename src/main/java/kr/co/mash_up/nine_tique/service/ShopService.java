package kr.co.mash_up.nine_tique.service;

import org.springframework.data.domain.Page;

import kr.co.mash_up.nine_tique.dto.ShopDto;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.ShopRequestVO;

/**
 * Shop과 관련된 비즈니스 로직 처리
 * <p>
 * Created by ethankim on 2017. 7. 7..
 */
public interface ShopService {

    /**
     * 매장 정보 추가
     *
     * @param requestVO 추가할 매장 정보
     * @return
     */
    public abstract void addShop(ShopRequestVO requestVO);

    /**
     * 매장 정보 수정
     *
     * @param userId
     * @param shopId    Shop ID
     * @param requestVO 수정할 매장 정보
     * @return
     */
    public abstract void modifyShop(Long userId, Long shopId, ShopRequestVO requestVO);

    /**
     * 매장 정보 삭제
     *
     * @param shopId Shop ID
     */
    public abstract void removeShop(Long shopId);

    /**
     * 매장 리스트 조회
     *
     * @param requestVO 페이징 정보
     * @return
     */
    public abstract Page<ShopDto> readShops(DataListRequestVO requestVO);

    /**
     * 매장 상세정보 조회
     *
     * @param shopId Shop ID
     * @return 매장 상세정보
     */
    public abstract ShopDto readShop(Long shopId);
}
