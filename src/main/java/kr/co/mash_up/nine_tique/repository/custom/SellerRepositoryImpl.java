package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.mash_up.nine_tique.domain.QProduct;
import kr.co.mash_up.nine_tique.domain.QSeller;
import kr.co.mash_up.nine_tique.domain.QSellerProduct;
import kr.co.mash_up.nine_tique.domain.QUser;
import kr.co.mash_up.nine_tique.domain.Seller;
import kr.co.mash_up.nine_tique.domain.SellerProduct;
import lombok.RequiredArgsConstructor;

/**
 * SellerRepositoryCustom 구현체
 */
@RequiredArgsConstructor
public class SellerRepositoryImpl implements SellerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QUser USER = QUser.user;

    private static final QSeller SELLER = QSeller.seller;

    private static final QSellerProduct SELLER_PRODUCT = QSellerProduct.sellerProduct;

    private static final QProduct PRODUCT = QProduct.product;

    @Override
    public Optional<Seller> findOneByUserId(Long userId) {

        Seller seller =
                queryFactory.selectFrom(SELLER)
                        .join(SELLER.user, USER)
                        .where(USER.id.eq(userId))
                        .fetchOne();

        return Optional.ofNullable(seller);
    }

    @Override
    public List<SellerProduct> findSellerProducts(Long userId) {

        return queryFactory.selectFrom(SELLER_PRODUCT)
                .join(SELLER_PRODUCT.seller, SELLER)
                .join(SELLER.user, USER)
                .where(USER.id.eq(userId))
                .orderBy(SELLER_PRODUCT.id.productId.desc())
                .fetch();
    }

    @Override
    public Page<SellerProduct> findSellerProducts(Long userId, Pageable pageable) {

        QueryResults<SellerProduct> results =
                queryFactory.selectFrom(SELLER_PRODUCT)
                        .join(SELLER_PRODUCT.seller, SELLER)
                        .join(SELLER.user, USER)
                        .join(SELLER_PRODUCT.product, PRODUCT)
                        .where(USER.id.eq(userId))
                        .orderBy(PRODUCT.status.asc(), PRODUCT.id.desc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
