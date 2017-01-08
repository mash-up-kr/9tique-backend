package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "productRepository")
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
