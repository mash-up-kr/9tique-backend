package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Seller;
import kr.co.mash_up.nine_tique.repository.custom.SellerRepositoryCustom;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long>, SellerRepositoryCustom {

    /**
     * 인증코드로 판매자를 조회한다
     *
     * @param authentiCode 인증코드
     * @return
     */
    public abstract Optional<Seller> findByAuthentiCode(String authentiCode);
}
