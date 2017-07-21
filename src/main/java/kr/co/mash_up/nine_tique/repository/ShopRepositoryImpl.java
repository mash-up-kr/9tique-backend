package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.QSeller;
import kr.co.mash_up.nine_tique.domain.QShop;
import kr.co.mash_up.nine_tique.domain.QUser;
import kr.co.mash_up.nine_tique.domain.Shop;

/**
 * ShopRepositoryCustom 구현체
 */
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QShop SHOP = QShop.shop;

    private static final QUser USER = QUser.user;

    private static final QSeller SELLER = QSeller.seller;

    @Override
    public Optional<Shop> findOneByShopId(Long shopId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(SHOP)
                .where(SHOP.id.eq(shopId));

        return Optional.ofNullable(query.uniqueResult(SHOP));
    }

    @Override
    public Shop findByUserId(Long userId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(SELLER)
                .join(SELLER.shop, SHOP)
                .join(SELLER.user, USER)
                .where(USER.id.eq(userId))
                .limit(1L);

        return query.list(SHOP).get(0);
    }

    @Override
    public Page<Shop> findShops(Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(SHOP)
                .orderBy(SHOP.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return new PageImpl<>(query.list(SHOP), pageable, query.count());
    }
}
