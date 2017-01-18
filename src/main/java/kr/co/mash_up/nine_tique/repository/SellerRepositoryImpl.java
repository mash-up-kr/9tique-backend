package kr.co.mash_up.nine_tique.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * SellerRepositoryCustom 구현체
 */
public class SellerRepositoryImpl implements SellerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Seller findByUserId(Long userId) {
        JPAQuery query = new JPAQuery(entityManager);
        QUser qUser = QUser.user;
        QSeller qSeller = QSeller.seller;

        query.from(qSeller).join(qSeller.user, qUser)
                .where(qUser.id.eq(userId))
                .limit(1L);

        return query.list(qSeller).get(0);
    }

    @Override
    public List<SellerProduct> getSellerProducts(Long userId) {
        JPAQuery query = new JPAQuery(entityManager);
        QSellerProduct qSellerProduct = QSellerProduct.sellerProduct;
        QSeller qSeller = QSeller.seller;
        QUser qUser = QUser.user;

        query.from(qSellerProduct).join(qSellerProduct.seller, qSeller)
                .join(qSeller.user, qUser)
                .where(qUser.id.eq(userId))
                .orderBy(qSellerProduct.createdAt.desc());

        return query.list(qSellerProduct);
    }

    @Override
    public Page<SellerProduct> getSellerProducts(Long userId, Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);
        QSellerProduct qSellerProduct = QSellerProduct.sellerProduct;
        QSeller qSeller = QSeller.seller;
        QUser qUser = QUser.user;

        query.from(qSellerProduct).join(qSellerProduct.seller, qSeller)
                .join(qSeller.user, qUser)
                .where(qUser.id.eq(userId))
                .orderBy(qSeller.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return new PageImpl<SellerProduct>(query.list(qSellerProduct), pageable, query.count());
    }
}
