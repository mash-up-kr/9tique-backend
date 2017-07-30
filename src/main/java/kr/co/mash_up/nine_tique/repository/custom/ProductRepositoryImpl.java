package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.QCategory;
import kr.co.mash_up.nine_tique.domain.QProduct;


public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QProduct PRODUCT = QProduct.product;

    private static final QCategory CATEGORY = QCategory.category;

    @Override
    public Optional<Product> findOneByProductId(Long productId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(PRODUCT)
                .where(PRODUCT.id.eq(productId)
                        .and(PRODUCT.active.isTrue()));

        return Optional.ofNullable(query.uniqueResult(PRODUCT));
    }

    @Override
    public Page<Product> findByCategory(Pageable pageable, Category category) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(PRODUCT)
                .join(PRODUCT.category, CATEGORY)
                .where(CATEGORY.id.eq(category.getId()).and(PRODUCT.active.isTrue()))
                .orderBy(PRODUCT.status.asc(), PRODUCT.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        return new PageImpl<>(query.list(PRODUCT), pageable, query.count());
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(PRODUCT)
                .where(PRODUCT.active.isTrue())
                .orderBy(PRODUCT.status.asc(), PRODUCT.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        
        return new PageImpl<>(query.list(PRODUCT), pageable, query.count());
    }

    @Override
    public Page<Product> findByMainCategory(Pageable pageable, String mainCategory) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(PRODUCT)
                .join(PRODUCT.category, CATEGORY)
                .where(CATEGORY.main.eq(mainCategory).and(CATEGORY.active.isTrue()).and(PRODUCT.active.isTrue()))
                .orderBy(PRODUCT.status.asc(), PRODUCT.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return new PageImpl<>(query.list(PRODUCT), pageable, query.count());
    }
}
