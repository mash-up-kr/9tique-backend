package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.mash_up.nine_tique.domain.QUser;
import kr.co.mash_up.nine_tique.domain.QZzim;
import kr.co.mash_up.nine_tique.domain.QZzimProduct;
import kr.co.mash_up.nine_tique.domain.ZzimProduct;
import lombok.RequiredArgsConstructor;

/**
 * ZzimRepositoryCustom 구현체
 */
@RequiredArgsConstructor
public class ZzimRepositoryImpl implements ZzimRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QZzim ZZIM = QZzim.zzim;

    private static final QUser USER = QUser.user;

    private static final QZzimProduct ZZIM_PRODUCT = QZzimProduct.zzimProduct;

    @Override
    public Optional<ZzimProduct> findZzimProduct(Long userId, Long productId) {

        ZzimProduct zzimProduct =
                queryFactory.selectFrom(ZZIM_PRODUCT)
                        .join(ZZIM_PRODUCT.zzim, ZZIM)
                        .where(ZZIM.user.id.eq(userId)
                                .and(ZZIM_PRODUCT.product.id.eq(productId)))
                        .fetchOne();

        return Optional.ofNullable(zzimProduct);
    }

    @Override
    public Page<ZzimProduct> findZzimProducts(Long userId, Pageable pageable) {

        QueryResults<ZzimProduct> zzimProductQueryResults =
                queryFactory.selectFrom(ZZIM_PRODUCT)
                        .join(ZZIM_PRODUCT.zzim, ZZIM)
                        .join(ZZIM.user, USER)
                        .where(USER.id.eq(userId))
                        .orderBy(ZZIM_PRODUCT.product.id.desc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        return new PageImpl<>(zzimProductQueryResults.getResults(), pageable, zzimProductQueryResults.getTotal());
    }

    @Override
    public List<ZzimProduct> findZzimProducts(Long userId) {

        return queryFactory.selectFrom(ZZIM_PRODUCT)
                .join(ZZIM_PRODUCT.zzim, ZZIM)
                .join(ZZIM.user, USER)
                .where(USER.id.eq(userId))
                .orderBy(ZZIM_PRODUCT.product.id.desc())
                .fetch();
    }
}
