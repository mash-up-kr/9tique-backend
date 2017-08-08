package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Product;

public interface ProductRepositoryCustom {

    /**
     * 상품 ID로 조회한다
     *
     * @param productId
     * @return
     */
    public abstract Optional<Product> findOneByProductId(Long productId);

    /**
     * 상품 리스트를 조회한다
     *
     * @param pageable
     * @return
     */
    public abstract Page<Product> findProducts(Pageable pageable);

    /**
     * 카테고리별 상품 리스트를 조회한다
     *
     * @param category
     * @param pageable
     * @return
     */
    public abstract Page<Product> findProductsByCategory(Category category, Pageable pageable);

    /**
     * 메인 카테고리별 상품 리스트를 조회한다
     *
     * @param mainCategory
     * @param pageable
     * @return
     */
    public abstract Page<Product> findProductsByMainCategory(String mainCategory, Pageable pageable);

    /**
     * 게시물의 상품 리스트를 조회한다
     *
     * @param postId   Post ID
     * @param pageable
     * @return
     */
    public abstract Page<Product> findPostProducts(Long postId, Pageable pageable);

    /**
     * 매장의 상품 리스트를 조회한다
     *
     * @param shopId   Shop ID
     * @param pageable
     * @return
     */
    public abstract Page<Product> findShopProducts(Long shopId, Pageable pageable);

    /**
     * 카테고리별 매장의 상품 리스트를 조회한다
     *
     * @param shopId   Shop ID
     * @param category 카테고리
     * @param pageable
     * @return
     */

    public abstract Page<Product> findShopProductsByCategory(Long shopId, Category category, Pageable pageable);

    /**
     * 메인 카테고리별 매장의 상품 리스트를 조회한다
     *
     * @param shopId       Shop ID
     * @param mainCategory 메인 카테고리 이름
     * @param pageable
     * @return
     */
    public abstract Page<Product> findShopProductsByMainCategory(Long shopId, String mainCategory, Pageable pageable);

    /**
     * 프로모션의 상품 리스트를 조회한다
     *
     * @param promotionId 프로모션 ID
     * @param pageable
     * @return
     */
    public abstract Page<Product> findPromotionProducts(Long promotionId, Pageable pageable);

    /**
     * 카테고리별 프로모션의 상품 리스트를 조회한다
     *
     * @param promotionId 프로모션 ID
     * @param category    카테고리
     * @param pageable
     * @return
     */

    public abstract Page<Product> findPromotionProductsByCategory(Long promotionId, Category category, Pageable pageable);

    /**
     * 메인 카테고리별 프로모션의 상품 리스트를 조회한다
     *
     * @param promotionId  프로모션 ID
     * @param mainCategory 메인 카테고리 이름
     * @param pageable
     * @return
     */
    public abstract Page<Product> findPromotionProductsByMainCategory(Long promotionId, String mainCategory, Pageable pageable);

    /**
     * 브랜드의 상품 리스트를 조회한다
     *
     * @param brandId  브랜드 ID
     * @param pageable
     * @return
     */
    public abstract Page<Product> findBrandProducts(Long brandId, Pageable pageable);

    /**
     * 카테고리별 브랜드의 상품 리스트를 조회한다
     *
     * @param brandId  브랜드 ID
     * @param category 카테고리
     * @param pageable
     * @return
     */
    public abstract Page<Product> findBrandProductsByCategory(Long brandId, Category category, Pageable pageable);

    /**
     * 메인 카테고리별 브랜드의 상품 리스트를 조회한다
     *
     * @param brandId      브랜드 ID
     * @param mainCategory 메인 카테고리 이름
     * @param pageable
     * @return
     */
    public abstract Page<Product> findBrandProductsByMainCategory(Long brandId, String mainCategory, Pageable pageable);
}
