package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.mash_up.nine_tique.domain.Promotion;

/**
 * Created by ethankim on 2017. 7. 9..
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

}
