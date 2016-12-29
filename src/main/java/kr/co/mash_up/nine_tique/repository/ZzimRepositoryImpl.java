package kr.co.mash_up.nine_tique.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.QUser;
import kr.co.mash_up.nine_tique.domain.QZzim;
import kr.co.mash_up.nine_tique.domain.QZzimProduct;
import kr.co.mash_up.nine_tique.domain.ZzimProduct;
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
        QZzimProduct zzimProduct = QZzimProduct.zzimProduct;
        QZzim zzim = QZzim.zzim;
        QUser user = QUser.user;

        query.from(zzimProduct).join(zzimProduct.zzim, zzim)
                .join(zzim.user, user)
                .where(user.id.eq(userId))
                .orderBy(zzimProduct.createdAt.desc())
                .limit(pageable.getPageSize()).offset(pageable.getOffset());

        return new PageImpl<ZzimProduct>(query.list(zzimProduct), pageable, query.count());
    }

    @Override
    public List<ZzimProduct> getZzimProducts(Long userId) {
        JPAQuery query = new JPAQuery(entityManager);
        QZzimProduct zzimProduct = QZzimProduct.zzimProduct;
        QZzim zzim = QZzim.zzim;
        QUser user = QUser.user;

        query.from(zzimProduct).join(zzimProduct.zzim, zzim)
                .join(zzim.user, user)
                .where(user.id.eq(userId))
                .orderBy(zzimProduct.createdAt.desc());

        return query.list(zzimProduct);
    }
}
