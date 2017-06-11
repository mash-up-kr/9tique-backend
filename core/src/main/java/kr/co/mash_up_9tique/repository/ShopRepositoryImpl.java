package kr.co.mash_up_9tique.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up_9tique.domain.QSeller;
import kr.co.mash_up_9tique.domain.QShop;
import kr.co.mash_up_9tique.domain.QUser;
import kr.co.mash_up_9tique.domain.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

    @Override
    public Page<Shop> findShops(Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);
        QShop qShop = QShop.shop;

        query.from(qShop)
                .where(qShop.enabled.isTrue())
                .orderBy(qShop.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return new PageImpl<Shop>(query.list(qShop), pageable, query.count());
    }
}
