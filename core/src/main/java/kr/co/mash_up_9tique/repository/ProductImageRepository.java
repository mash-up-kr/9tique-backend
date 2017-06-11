package kr.co.mash_up_9tique.repository;


import kr.co.mash_up_9tique.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    ProductImage findByFileName(String fileName);
}