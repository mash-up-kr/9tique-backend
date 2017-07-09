package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.ShopComment;

/**
 * Created by ethankim on 2017. 7. 8..
 */
public interface ShopCommentRepositoryCustom {

    /**
     * Comment ID로 단건 조회
     *
     * @param shopId    Shop ID
     * @param commentId Comment ID
     * @return
     */
    public abstract Optional<ShopComment> findOneByShopIdAndCommentId(Long shopId, Long commentId);

    /**
     * 매장의 댓글 리스트 조회
     *
     * @param shopId   Shop ID
     * @param pageable
     * @return
     */
    public abstract Page<ShopComment> findShopComments(Long shopId, Pageable pageable);
}
