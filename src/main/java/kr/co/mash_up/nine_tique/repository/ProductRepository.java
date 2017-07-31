package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.repository.custom.ProductRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

}
