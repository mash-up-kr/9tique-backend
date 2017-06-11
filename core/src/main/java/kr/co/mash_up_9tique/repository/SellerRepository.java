package kr.co.mash_up_9tique.repository;

import kr.co.mash_up_9tique.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "sellerRepository")
public interface SellerRepository extends JpaRepository<Seller, Long>, SellerRepositoryCustom {

    Seller findByAuthentiCode(String authentiCode);
}
