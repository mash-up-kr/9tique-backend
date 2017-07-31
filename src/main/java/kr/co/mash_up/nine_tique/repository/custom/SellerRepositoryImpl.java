package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.QProduct;
import kr.co.mash_up.nine_tique.domain.QSeller;
import kr.co.mash_up.nine_tique.domain.QSellerProduct;
import kr.co.mash_up.nine_tique.domain.QUser;
import kr.co.mash_up.nine_tique.domain.Seller;
import kr.co.mash_up.nine_tique.domain.SellerProduct;

/**
 * SellerRepositoryCustom 구현체
 */
public class SellerRepositoryImpl implements SellerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QUser USER = QUser.user;

    private static final QSeller SELLER = QSeller.seller;

    private static final QSellerProduct SELLER_PRODUCT = QSellerProduct.sellerProduct;

    private static final QProduct PRODUCT = QProduct.product;

    @Override
    public Optional<Seller> findOneByUserId(Long userId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(SELLER)
                .join(SELLER.user, USER)
                .where(USER.id.eq(userId));

        return Optional.ofNullable(query.uniqueResult(SELLER));
    }

    @Override
    public List<SellerProduct> findSellerProducts(Long userId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(SELLER_PRODUCT)
                .join(SELLER_PRODUCT.seller, SELLER)
                .join(SELLER.user, USER)
                .where(USER.id.eq(userId))
                .orderBy(SELLER_PRODUCT.id.productId.desc());

        return query.list(SELLER_PRODUCT);
    }

    @Override
    public Page<SellerProduct> findSellerProducts(Long userId, Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(SELLER_PRODUCT)
                .join(SELLER_PRODUCT.seller, SELLER)
                .join(SELLER.user, USER)
                .join(SELLER_PRODUCT.product, PRODUCT)
                .where(USER.id.eq(userId))
                .orderBy(PRODUCT.status.asc(), PRODUCT.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return new PageImpl<>(query.list(SELLER_PRODUCT), pageable, query.count());
    }
}
