package kr.co.mash_up.nine_tique.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.QSeller;
import kr.co.mash_up.nine_tique.domain.QShop;
import kr.co.mash_up.nine_tique.domain.QUser;
import kr.co.mash_up.nine_tique.domain.Shop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * ShopRepositoryCustom 구현체
 */
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

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
}
