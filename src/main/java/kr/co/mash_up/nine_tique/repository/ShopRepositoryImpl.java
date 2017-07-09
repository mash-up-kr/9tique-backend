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

    @Override
    public Optional<Shop> findOneByShopId(Long shopId) {
        JPAQuery query = new JPAQuery(entityManager);
        QShop qShop = QShop.shop;

        query.from(qShop)
                .where(qShop.id.eq(shopId)
                        .and(qShop.active.isTrue()));

        return Optional.ofNullable(query.uniqueResult(qShop));
    }

    @Override
    public Shop findByUserId(Long userId) {
        JPAQuery query = new JPAQuery(entityManager);
        QUser qUser = QUser.user;
        QSeller qSeller = QSeller.seller;
        QShop qShop = QShop.shop;

        query.from(qSeller)
                .join(qSeller.shop, qShop)
                .join(qSeller.user, qUser)
                .where(qUser.id.eq(userId))
                .limit(1L);

        return query.list(qShop).get(0);
    }

    @Override
    public Page<Shop> findShops(Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);
        QShop qShop = QShop.shop;

        query.from(qShop)
                .where(qShop.active.isTrue())
                .orderBy(qShop.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return new PageImpl<Shop>(query.list(qShop), pageable, query.count());
    }
}
