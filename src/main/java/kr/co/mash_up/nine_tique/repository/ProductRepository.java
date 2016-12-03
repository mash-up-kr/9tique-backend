package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByOrderByCreatedAtDesc(Pageable pageable);


}
