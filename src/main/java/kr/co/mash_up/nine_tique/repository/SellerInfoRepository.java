package kr.co.mash_up.nine_tique.repository;


import kr.co.mash_up.nine_tique.domain.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, Long> {

    SellerInfo findByshopName(String shopName);

    SellerInfo findByphone(String phone);
}
