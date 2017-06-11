package kr.co.mash_up_9tique.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up_9tique.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


/**
 * ZzimRepositoryCustom 구현체
 */
public class ZzimRepositoryImpl implements ZzimRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ZzimProduct> getZzimProducts(Long userId, Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);
        QZzimProduct qZzimProduct = QZzimProduct.zzimProduct;
        QZzim qZzim = QZzim.zzim;
        QUser qUser = QUser.user;

        query.from(qZzimProduct).join(qZzimProduct.zzim, qZzim)
                .join(qZzim.user, qUser)
                .where(qUser.id.eq(userId).and(qZzimProduct.enabled.isTrue()))
                .orderBy(qZzimProduct.createdAt.desc())
                .limit(pageable.getPageSize()).offset(pageable.getOffset());

        return new PageImpl<ZzimProduct>(query.list(qZzimProduct), pageable, query.count());
    }

    @Override
    public List<ZzimProduct> getZzimProducts(Long userId) {
        JPAQuery query = new JPAQuery(entityManager);
        QZzimProduct qZzimProduct = QZzimProduct.zzimProduct;
        QZzim qZzim = QZzim.zzim;
        QUser qUser = QUser.user;

        query.from(qZzimProduct).join(qZzimProduct.zzim, qZzim)
                .join(qZzim.user, qUser)
                .where(qUser.id.eq(userId).and(qZzimProduct.enabled.isTrue()))
                .orderBy(qZzimProduct.createdAt.desc());

        return query.list(qZzimProduct);
    }

    @Override
    public ZzimProduct getZzimProduct(Zzim zzim, Product product) {
        JPAQuery query = new JPAQuery(entityManager);
        QZzimProduct qZzimProduct = QZzimProduct.zzimProduct;

        query.from(qZzimProduct)
                .where(qZzimProduct.zzim.eq(zzim).and(qZzimProduct.product.eq(product)));

        return query.uniqueResult(qZzimProduct);
    }
}
