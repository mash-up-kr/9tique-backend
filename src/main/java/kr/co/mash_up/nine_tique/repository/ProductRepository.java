package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.SellerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> /*, ProductRepositoryCustom */{
    Page<Product> findByCategory(Pageable pageable, Category category);

    Page<Product> findByCategoryOrderByCreatedAtDesc(Pageable pageable, Category category);
}