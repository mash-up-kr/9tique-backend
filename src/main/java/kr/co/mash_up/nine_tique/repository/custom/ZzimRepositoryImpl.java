package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.QUser;
import kr.co.mash_up.nine_tique.domain.QZzim;
import kr.co.mash_up.nine_tique.domain.QZzimProduct;
import kr.co.mash_up.nine_tique.domain.ZzimProduct;

/**
 * ZzimRepositoryCustom 구현체
 */
public class ZzimRepositoryImpl implements ZzimRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QZzim ZZIM = QZzim.zzim;

    private static final QUser USER = QUser.user;

    private static final QZzimProduct ZZIM_PRODUCT = QZzimProduct.zzimProduct;

    @Override
    public Optional<ZzimProduct> getZzimProduct(Long userId, Long productId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(ZZIM_PRODUCT)
                .join(ZZIM_PRODUCT.zzim, ZZIM)
                .where(ZZIM.user.id.eq(userId)
                        .and(ZZIM_PRODUCT.product.id.eq(productId)));

        return Optional.ofNullable(query.uniqueResult(ZZIM_PRODUCT));
    }

    @Override
    public Page<ZzimProduct> getZzimProducts(Long userId, Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(ZZIM_PRODUCT)
                .join(ZZIM_PRODUCT.zzim, ZZIM)
                .join(ZZIM.user, USER)
                .where(USER.id.eq(userId))
                .orderBy(ZZIM_PRODUCT.product.id.desc())
                .limit(pageable.getPageSize()).offset(pageable.getOffset());

        return new PageImpl<>(query.list(ZZIM_PRODUCT), pageable, query.count());
    }

    @Override
    public List<ZzimProduct> getZzimProducts(Long userId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(ZZIM_PRODUCT)
                .join(ZZIM_PRODUCT.zzim, ZZIM)
                .join(ZZIM.user, USER)
                .where(USER.id.eq(userId))
                .orderBy(ZZIM_PRODUCT.product.id.desc());

        return query.list(ZZIM_PRODUCT);
    }
}
