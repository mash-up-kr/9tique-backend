package kr.co.mash_up.nine_tique.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Shop;
import kr.co.mash_up.nine_tique.repository.custom.ShopRepositoryCustom;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {

    /**
     * Name으로 Shop을 조회한다
     *
     * @param name
     * @return
     */
    @Deprecated
    public abstract Shop findByName(String name);

    /**
     * PhoneNumber로 Shop을 조회한다
     *
     * @param phoneNumber
     * @return
     */
    @Deprecated
    public abstract Shop findByPhoneNumber(String phoneNumber);

    /**
     * Name, PhoneNumber으로 Shop을 조회한다
     *
     * @param name
     * @param phoneNumber
     * @return
     */
    public abstract Optional<Shop> findByNameAndPhoneNumber(String name, String phoneNumber);
}
