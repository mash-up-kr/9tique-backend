package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.QCategory;
import kr.co.mash_up.nine_tique.domain.QProduct;
import lombok.RequiredArgsConstructor;

/**
 * Created by ethankim on 2016. 11. 12..
 */
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QProduct PRODUCT = QProduct.product;

    private static final QCategory CATEGORY = QCategory.category;

    @Override
    public Optional<Product> findOneByProductId(Long productId) {

        Product product =
                queryFactory.selectFrom(PRODUCT)
                        .where(PRODUCT.id.eq(productId))
                        .fetchOne();

        return Optional.ofNullable(product);
    }

    @Override
    public Page<Product> findByCategory(Pageable pageable, Category category) {

        QueryResults<Product> results =
                queryFactory.selectFrom(PRODUCT)
                        .join(PRODUCT.category, CATEGORY)
                        .where(CATEGORY.id.eq(category.getId()))
                        .orderBy(PRODUCT.status.asc(), PRODUCT.id.desc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {

        QueryResults<Product> results =
                queryFactory.selectFrom(PRODUCT)
                        .orderBy(PRODUCT.status.asc(), PRODUCT.id.desc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Product> findByMainCategory(Pageable pageable, String mainCategory) {

        QueryResults<Product> results =
                queryFactory.selectFrom(PRODUCT)
                        .join(PRODUCT.category, CATEGORY)
                        .where(CATEGORY.main.eq(mainCategory))
                        .orderBy(PRODUCT.status.asc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
