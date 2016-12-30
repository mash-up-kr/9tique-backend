package kr.co.mash_up.nine_tique.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.QCategory;
import kr.co.mash_up.nine_tique.domain.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Product> findByMainCategory(Pageable pageable, String mainCategory) {
        JPAQuery query = new JPAQuery(entityManager);
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;
//        QProductImage productImage = QProductImage.productImage;
//        QSellerInfo sellerInfo = QSellerInfo.sellerInfo;

        query.from(product)
                .join(product.category, category)
//                .join(product.sellerInfo, sellerInfo)
//                .join(product.productImages, productImage)
                .where(category.main.eq(mainCategory))
                .orderBy(product.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        return new PageImpl<Product>(query.list(product), pageable, query.count());
    }
}
