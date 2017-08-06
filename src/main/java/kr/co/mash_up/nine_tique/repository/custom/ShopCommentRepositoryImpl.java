package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.mash_up.nine_tique.domain.QShop;
import kr.co.mash_up.nine_tique.domain.QShopComment;
import kr.co.mash_up.nine_tique.domain.ShopComment;
import lombok.RequiredArgsConstructor;

/**
 * QueryDSL을 사용하여 ShopComment Data를 처리한다
 * <p>
 * Created by ethankim on 2017. 7. 8..
 */
@RequiredArgsConstructor
public class ShopCommentRepositoryImpl implements ShopCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QShopComment SHOP_COMMENT = QShopComment.shopComment;

    private static final QShop SHOP = QShop.shop;

    @Override
    public Optional<ShopComment> findOneByShopIdAndCommentId(Long shopId, Long commentId) {

        ShopComment comment = queryFactory.selectFrom(SHOP_COMMENT)
                .join(SHOP_COMMENT.shop, SHOP)
                .where(SHOP_COMMENT.id.eq(commentId)
                        .and(SHOP.id.eq(shopId)))
                .fetchOne();

        return Optional.ofNullable(comment);
    }

    @Override
    public Page<ShopComment> findShopComments(Long shopId, Pageable pageable) {

        QueryResults<ShopComment> results =
                queryFactory.selectFrom(SHOP_COMMENT)
                        .where(SHOP_COMMENT.shop.id.eq(shopId))
                        .orderBy(SHOP_COMMENT.id.desc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
