package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.mash_up.nine_tique.domain.QShop;
import kr.co.mash_up.nine_tique.domain.Shop;
import lombok.RequiredArgsConstructor;

/**
 * ShopRepositoryCustom 구현체
 */
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QShop SHOP = QShop.shop;

    @Override
    public Optional<Shop> findOneByShopId(Long shopId) {

        Shop shop = queryFactory.selectFrom(SHOP)
                .where(SHOP.id.eq(shopId))
                .fetchOne();

        return Optional.ofNullable(shop);
    }

    @Override
    public Page<Shop> findShops(Pageable pageable) {

        QueryResults<Shop> shopQueryResults =
                queryFactory.selectFrom(SHOP)
                        .orderBy(SHOP.id.desc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        return new PageImpl<>(shopQueryResults.getResults(), pageable, shopQueryResults.getTotal());
    }
}
