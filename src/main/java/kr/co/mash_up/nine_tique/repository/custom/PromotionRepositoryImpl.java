package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.mash_up.nine_tique.domain.Promotion;
import kr.co.mash_up.nine_tique.domain.QPromotion;
import lombok.RequiredArgsConstructor;

/**
 * Created by ethankim on 2017. 7. 12..
 */
@RequiredArgsConstructor
public class PromotionRepositoryImpl implements PromotionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QPromotion PROMOTION = QPromotion.promotion;

    @Override
    public Optional<Promotion> findOneByPromotionId(Long promotionId) {

        Promotion promotion =
                queryFactory.selectFrom(PROMOTION)
                        .where(PROMOTION.id.eq(promotionId))
                        .fetchOne();

        return Optional.ofNullable(promotion);
    }

    @Override
    public Page<Promotion> findPromotions(Pageable pageable) {

        QueryResults<Promotion> results =
                queryFactory.selectFrom(PROMOTION)
                        .orderBy(PROMOTION.id.desc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
