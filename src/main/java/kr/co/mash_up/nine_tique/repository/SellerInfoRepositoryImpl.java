package kr.co.mash_up.nine_tique.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.QSellerInfo;
import kr.co.mash_up.nine_tique.domain.QUser;
import kr.co.mash_up.nine_tique.domain.SellerInfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * SellerInfoRepositoryCustom 구현체
 */
public class SellerInfoRepositoryImpl implements SellerInfoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SellerInfo findByUserId(Long userId) {
        JPAQuery query = new JPAQuery(entityManager);
        QUser user = QUser.user;
        QSellerInfo sellerInfo = QSellerInfo.sellerInfo;

       query.from(sellerInfo).join(sellerInfo.user, user)
                .where(user.id.eq(userId))
                .limit(1L);

        return query.list(sellerInfo).get(0);
    }
}
