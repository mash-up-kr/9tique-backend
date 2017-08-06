package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Post;
import kr.co.mash_up.nine_tique.domain.PostProduct;


/**
 * Created by ethankim on 2017. 7. 25..
 */
public interface PostRepositoryCustom {

    /**
     * Post 단건 조회
     *
     * @param postId Post ID
     * @return
     */
    public abstract Optional<Post> findOneByPostId(Long postId);

    /**
     * Post 리스트 조회
     *
     * @param pageable
     * @return
     */
    public abstract Page<Post> findPosts(Pageable pageable);

    /**
     * 게시물의 상품 리스트 조회
     *
     * @param postId   Post ID
     * @param pageable
     * @return
     */
    public abstract Page<PostProduct> findPostProducts(Long postId, Pageable pageable);
}
