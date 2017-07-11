package kr.co.mash_up.nine_tique.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.Promotion;
import kr.co.mash_up.nine_tique.domain.QPromotion;

/**
 * Created by ethankim on 2017. 7. 12..
 */
public class PromotionRepositoryImpl implements PromotionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QPromotion PROMOTION = QPromotion.promotion;

    @Override
    public Optional<Promotion> findOnebyPromotionId(Long promotionId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(PROMOTION)
                .where(PROMOTION.id.eq(promotionId)
                        .and(PROMOTION.active.isTrue()));

        return Optional.ofNullable(query.uniqueResult(PROMOTION));
    }
}
