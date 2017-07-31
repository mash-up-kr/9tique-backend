package kr.co.mash_up.nine_tique.repository.custom;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.PostComment;

/**
 * Created by ethankim on 2017. 7. 25..
 */
public interface PostCommentRepositoryCustom {

    /**
     * Comment ID로 단건 조회
     *
     * @param postId    Post ID
     * @param commentId Comment ID
     * @return
     */
    public abstract Optional<PostComment> findOneByPostIdAndCommentId(Long postId, Long commentId);

    /**
     * 게시물의 댓글 리스트 조회
     *
     * @param postId   Post ID
     * @param pageable
     * @return
     */
    public abstract Page<PostComment> findPostComments(Long postId, Pageable pageable);
}
