package kr.co.mash_up.nine_tique.service;

import org.springframework.data.domain.Page;

import kr.co.mash_up.nine_tique.dto.PromotionDto;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.PromotionRequestVO;

/**
 * Promotion과 관련된 비즈니스 로직 처리
 * <p>
 * Created by ethankim on 2017. 7. 9..
 */
public interface PromotionService {

    /**
     * 프로모션 추가
     *
     * @param registerUserId 프로모션 추가 유저 ID
     * @param promotionVO    추가할 프로모션 정보
     */
    public abstract void addPromotion(Long registerUserId, PromotionRequestVO promotionVO);

    /**
     * 프로모션 수정
     *
     * @param promotionId 프로모션 ID
     * @param promotionVO 수정할 프로모션 정보
     */
    public abstract void modifyPromotion(Long promotionId, PromotionRequestVO promotionVO);

    /**
     * 프로모션 삭제
     *
     * @param promotionId 프로모션 ID
     */
    public abstract void removePromotion(Long promotionId);

    /**
     * 프로모션 리스트 조회
     *
     * @param requestVO 페이징 정보
     * @return
     */
    public abstract Page<PromotionDto> readPromotions(DataListRequestVO requestVO);

    /**
     * 프로모션 상세정보 조회
     *
     * @param promotionId 프로모션 ID
     * @return
     */
    public abstract PromotionDto readPromotion(Long promotionId);
}
