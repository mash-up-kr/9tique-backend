package kr.co.mash_up.nine_tique.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.Category;
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
    public Page<Product> findByCategory(Pageable pageable, Category category) {
        JPAQuery query = new JPAQuery(entityManager);
        QProduct qProduct = QProduct.product;
        QCategory qCategory = QCategory.category;

        query.from(qProduct)
                .join(qProduct.category, qCategory)
                .where(qCategory.id.eq(category.getId()))
                .orderBy(qProduct.status.asc(), qProduct.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        return new PageImpl<Product>(query.list(qProduct), pageable, query.count());
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);
        QProduct qProduct = QProduct.product;

        query.from(qProduct)
                .orderBy(qProduct.status.asc(), qProduct.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        return new PageImpl<Product>(query.list(qProduct), pageable, query.count());
    }

    @Override
    public Page<Product> findByMainCategory(Pageable pageable, String mainCategory) {
        JPAQuery query = new JPAQuery(entityManager);
        QProduct qProduct = QProduct.product;
        QCategory qCategory = QCategory.category;

        query.from(qProduct)
                .join(qProduct.category, qCategory)
                .where(qCategory.main.eq(mainCategory))
                .orderBy(qProduct.status.asc(), qProduct.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        return new PageImpl<Product>(query.list(qProduct), pageable, query.count());
    }
}
