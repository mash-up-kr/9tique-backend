package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



//public class ProductRepositoryImpl implements ProductRepositoryCustom {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    //Todo: join user, sellerinfo, product, category, productimage
//    @Override
//    public Page<Product> findByCategory(Pageable pageable, Category category) {
//        JPAQuery query = new JPAQuery(entityManager);
//        QProduct qProduct = QProduct.product;
//        QCategory qCategory = QCategory.category;
//        QProductImage qProductImage = QProductImage.productImage;
//        QSellerInfo qSellerInfo = QSellerInfo.sellerInfo;
//        QUser qUser = QUser.user;
//
//        query.from(qProduct)
//                .join(qProduct.category, qCategory)
////                .join(qProduct.sellerInfo, qSellerInfo)
//                .join(qProduct.sellerInfo.user, qUser)
//                .join(qProduct.productImages, qProductImage)
//                .where(qCategory.id.eq(category.getId()))
//                .orderBy(qProduct.createdAt.desc())
//                .limit(pageable.getPageSize())
//                .offset(pageable.getOffset());
//
//        return new PageImpl<Product>(query.list(qProduct), pageable, query.count());
//    }
//}
