package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "sellerRepository")
public interface SellerRepository extends JpaRepository<Seller, Seller.Id> {
}
