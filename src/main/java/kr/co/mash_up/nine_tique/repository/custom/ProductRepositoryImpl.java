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
import kr.co.mash_up.nine_tique.domain.QPromotionProduct;
import kr.co.mash_up.nine_tique.domain.QShop;

/**
 * Created by ethankim on 2016. 11. 12..
 */
public class ProductRepositoryImpl extends QueryDslRepositorySupport implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QProduct PRODUCT = QProduct.product;

    private static final QCategory CATEGORY = QCategory.category;

    private static final QPostProduct POST_PRODUCT = QPostProduct.postProduct;

    private static final QShop SHOP = QShop.shop;

    private static final QPromotionProduct PROMOTION_PRODUCT = QPromotionProduct.promotionProduct;

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
    public Page<Product> findProducts(Pageable pageable) {

        QueryResults<Product> results =
                queryFactory.selectFrom(PRODUCT)
                        .orderBy(PRODUCT.status.asc(), PRODUCT.id.desc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Product> findProductsByCategory(Category category, Pageable pageable) {

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
    public Page<Product> findProductsByMainCategory(String mainCategory, Pageable pageable) {

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

    @Override
    public Page<Product> findShopProducts(Long shopId, Pageable pageable) {

        JPAQuery<Product> query =
                queryFactory.selectFrom(PRODUCT)
                        .join(PRODUCT.shop, SHOP)
                        .where(SHOP.id.eq(shopId))
                        .orderBy(PRODUCT.status.asc());

        QueryResults<Product> results = getQuerydsl().applyPagination(pageable, query).fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Product> findShopProductsByCategory(Long shopId, Category category, Pageable pageable) {

        JPAQuery<Product> query =
                queryFactory.selectFrom(PRODUCT)
                        .join(PRODUCT.shop, SHOP)
                        .join(PRODUCT.category, CATEGORY)
                        .where(SHOP.id.eq(shopId)
                                .and(CATEGORY.id.eq(category.getId())))
                        .orderBy(PRODUCT.status.asc());

        QueryResults<Product> results = getQuerydsl().applyPagination(pageable, query).fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Product> findShopProductsByMainCategory(Long shopId, String mainCategory, Pageable pageable) {

        JPAQuery<Product> query =
                queryFactory.selectFrom(PRODUCT)
                        .join(PRODUCT.shop, SHOP)
                        .join(PRODUCT.category, CATEGORY)
                        .where(SHOP.id.eq(shopId)
                                .and(CATEGORY.main.eq(mainCategory)))
                        .orderBy(PRODUCT.status.asc());

        QueryResults<Product> results = getQuerydsl().applyPagination(pageable, query).fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Product> findPromotionProducts(Long promotionId, Pageable pageable) {

        JPAQuery<Product> query =
                queryFactory.selectFrom(PRODUCT)
                        .join(PRODUCT.promotionProducts, PROMOTION_PRODUCT)
                        .where(PROMOTION_PRODUCT.promotion.id.eq(promotionId))
                        .orderBy(PRODUCT.status.asc());

        QueryResults<Product> results = getQuerydsl().applyPagination(pageable, query).fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Product> findPromotionProductsByCategory(Long promotionId, Category category, Pageable pageable) {

        JPAQuery<Product> query =
                queryFactory.selectFrom(PRODUCT)
                        .join(PRODUCT.promotionProducts, PROMOTION_PRODUCT)
                        .join(PRODUCT.category, CATEGORY)
                        .where(PROMOTION_PRODUCT.promotion.id.eq(promotionId)
                                .and(CATEGORY.id.eq(category.getId())))
                        .orderBy(PRODUCT.status.asc());

        QueryResults<Product> results = getQuerydsl().applyPagination(pageable, query).fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Product> findPromotionProductsByMainCategory(Long promotionId, String mainCategory, Pageable pageable) {

        JPAQuery<Product> query =
                queryFactory.selectFrom(PRODUCT)
                        .join(PRODUCT.promotionProducts, PROMOTION_PRODUCT)
                        .join(PRODUCT.category, CATEGORY)
                        .where(PROMOTION_PRODUCT.promotion.id.eq(promotionId)
                                .and(CATEGORY.main.eq(mainCategory)))
                        .orderBy(PRODUCT.status.asc());

        QueryResults<Product> results = getQuerydsl().applyPagination(pageable, query).fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
