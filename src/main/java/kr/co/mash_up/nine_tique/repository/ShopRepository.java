package kr.co.mash_up.nine_tique.repository;


import kr.co.mash_up.nine_tique.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "shopRepository")
public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {

    Shop findByName(String name);

    Shop findByPhone(String phone);

    Shop findByNameAndPhone(String name, String phone);

    Shop findByAuthentiCode(String authentiCode);
}
