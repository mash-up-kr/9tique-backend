package kr.co.mash_up.nine_tique.repository;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Promotion;

/**
 * Created by ethankim on 2017. 7. 12..
 */
public interface PromotionRepositoryCustom {

    /**
     * Promotion 단건 조회
     *
     * @param promotionId Promotion ID
     * @return
     */
    public abstract Optional<Promotion> findOnebyPromotionId(Long promotionId);
}
