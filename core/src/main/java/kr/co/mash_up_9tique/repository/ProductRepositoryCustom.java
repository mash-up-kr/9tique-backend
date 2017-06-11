package kr.co.mash_up_9tique.repository;

import kr.co.mash_up_9tique.domain.Category;
import kr.co.mash_up_9tique.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Product findOneById(Long id);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByCategory(Pageable pageable, Category category);

    Page<Product> findByMainCategory(Pageable pageable, String mainCategory);
}
