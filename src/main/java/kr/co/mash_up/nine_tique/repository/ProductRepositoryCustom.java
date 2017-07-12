package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Product;

public interface ProductRepositoryCustom {

    public abstract Product findOneByProductId(Long productId);

    public abstract Page<Product> findAll(Pageable pageable);

    public abstract Page<Product> findByCategory(Pageable pageable, Category category);

    public abstract Page<Product> findByMainCategory(Pageable pageable, String mainCategory);
}
