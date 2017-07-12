package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public abstract Optional<Promotion> findOneByPromotionId(Long promotionId);

    /**
     * 프로모션 리스트 조회
     *
     * @param pageable
     * @return
     */
    public abstract Page<Promotion> findPromotions(Pageable pageable);
}
