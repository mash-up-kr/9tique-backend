package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.QShop;
import kr.co.mash_up.nine_tique.domain.QShopComment;
import kr.co.mash_up.nine_tique.domain.ShopComment;

/**
 * QueryDSL을 사용하여 ShopComment Data를 처리한다
 * <p>
 * Created by ethankim on 2017. 7. 8..
 */
public class ShopCommentRepositoryImpl implements ShopCommentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QShopComment SHOP_COMMENT = QShopComment.shopComment;

    private static final QShop SHOP = QShop.shop;

    @Override
    public Optional<ShopComment> findOneByShopIdAndCommentId(Long shopId, Long commentId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(SHOP_COMMENT)
                .join(SHOP_COMMENT.shop, SHOP)
                .where(SHOP_COMMENT.id.eq(commentId)
                        .and(SHOP.id.eq(shopId))
                        .and(SHOP_COMMENT.active.isTrue()));

        return Optional.ofNullable(query.uniqueResult(SHOP_COMMENT));
    }

    @Override
    public Page<ShopComment> findShopComments(Long shopId, Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(SHOP_COMMENT)
                .where(SHOP_COMMENT.shop.id.eq(shopId)
                        .and(SHOP_COMMENT.active.isTrue()))
                .orderBy(SHOP_COMMENT.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return new PageImpl<>(query.list(SHOP_COMMENT), pageable, query.count());
    }
}
