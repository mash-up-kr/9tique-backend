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
    public abstract Page<Product> findAll(Pageable pageable);

    /**
     * 카테고리에 속한 상품 리스트를 조회한다
     *
     * @param pageable
     * @param category
     * @return
     */
    public abstract Page<Product> findByCategory(Pageable pageable, Category category);

    /**
     * 메인 카테고리에 속한 상품 리스트를 조회한다
     *
     * @param pageable
     * @param mainCategory
     * @return
     */
    public abstract Page<Product> findByMainCategory(Pageable pageable, String mainCategory);

    /**
     * 게시물의 상품 리스트를 조회한다
     *
     * @param postId   Post ID
     * @param pageable
     * @return
     */
    public abstract Page<Product> findPostProducts(Long postId, Pageable pageable);
}
