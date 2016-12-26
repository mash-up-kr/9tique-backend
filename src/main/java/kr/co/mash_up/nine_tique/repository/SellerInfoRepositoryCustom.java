package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.SellerInfo;

/**
 * Spring JPA에서 지원하지 않는 메소드를 QueryDSL을 이용하여 구현시 이용
 */
public interface SellerInfoRepositoryCustom {
    SellerInfo findByUserId(Long userId);
}
