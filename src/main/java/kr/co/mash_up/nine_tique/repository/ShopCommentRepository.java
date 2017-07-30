package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.mash_up.nine_tique.domain.ShopComment;
import kr.co.mash_up.nine_tique.repository.custom.ShopCommentRepositoryCustom;

/**
 * Created by ethankim on 2017. 7. 8..
 */
@Repository
public interface ShopCommentRepository extends JpaRepository<ShopComment, Long>, ShopCommentRepositoryCustom {

}
