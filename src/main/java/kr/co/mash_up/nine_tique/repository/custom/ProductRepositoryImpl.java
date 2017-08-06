package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.Optional;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.QCategory;
import kr.co.mash_up.nine_tique.domain.QPostProduct;
import kr.co.mash_up.nine_tique.domain.QProduct;

/**
 * Created by ethankim on 2016. 11. 12..
 */
public class ProductRepositoryImpl extends QueryDslRepositorySupport implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QProduct PRODUCT = QProduct.product;

    private static final QCategory CATEGORY = QCategory.category;

    private static final QPostProduct POST_PRODUCT = QPostProduct.postProduct;

    public ProductRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Product.class);
        this.queryFactory = queryFactory;
    }

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

    @Override
    public Page<Product> findPostProducts(Long postId, Pageable pageable) {

        JPAQuery<Product> query =
                queryFactory.selectFrom(PRODUCT)
                        .join(PRODUCT.postProducts, POST_PRODUCT)
                        .where(POST_PRODUCT.post.id.eq(postId))
                        .orderBy(PRODUCT.status.asc());

        QueryResults<Product> results = getQuerydsl().applyPagination(pageable, query).fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
