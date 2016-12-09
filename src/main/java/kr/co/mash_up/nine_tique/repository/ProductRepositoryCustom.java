package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> findByCategory(Pageable pageable, Category category);
}
