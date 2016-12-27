package kr.co.mash_up.nine_tique.repository;


import kr.co.mash_up.nine_tique.domain.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "sellerInfoRepository")
public interface SellerInfoRepository extends JpaRepository<SellerInfo, Long>, SellerInfoRepositoryCustom {

    SellerInfo findByshopName(String shopName);

    SellerInfo findByphone(String phone);
}
