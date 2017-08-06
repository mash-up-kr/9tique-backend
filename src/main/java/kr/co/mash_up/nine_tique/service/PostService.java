package kr.co.mash_up.nine_tique.service;

import org.springframework.data.domain.Page;

import kr.co.mash_up.nine_tique.web.dto.CommentDto;
import kr.co.mash_up.nine_tique.web.dto.PostDto;
import kr.co.mash_up.nine_tique.web.dto.ProductDto;
import kr.co.mash_up.nine_tique.web.vo.CommentRequestVO;
import kr.co.mash_up.nine_tique.web.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.web.vo.PostRequestVO;

/**
 * Post와 관련된 비즈니스 로직 처리
 * <p>
 * Created by ethankim on 2017. 7. 24..
 */
public interface PostService {

    /**
     * 게시물 정보 추가
     *
     * @param userId    작성자의 userId
     * @param requestVO 추가할 게시물 정보
     * @return
     */
    public abstract void addPost(Long userId, PostRequestVO requestVO);

    /**
     * 게시물 정보 수정
     *
     * @param userId    작성자의 userId
     * @param postId    Post ID
     * @param requestVO 수정할 게시물 정보
     * @return
     */
    public abstract void modifyPost(Long userId, Long postId, PostRequestVO requestVO);

    /**
     * 게시물 정보 삭제
     *
     * @param userId 작성자의 userIds
     * @param postId Post ID
     */
    public abstract void removePost(Long userId, Long postId);

    /**
     * 게시물 리스트 조회
     *
     * @param requestVO 페이징 정보
     * @return
     */
    public abstract Page<PostDto> readPosts(DataListRequestVO requestVO);

    /**
     * 게시물 상세정보 조회
     *
     * @param postId Post ID
     * @return 게시물 상세정보
     */
    public abstract PostDto readPost(Long postId);

    /**
     * 게시물 댓글 추가
     *
     * @param postId    Post ID
     * @param userId    댓글 작성자 ID
     * @param requestVO 추가할 댓글 정보
     */
    public abstract void addPostComment(Long postId, Long userId, CommentRequestVO requestVO);

    /**
     * 게시물 댓글 수정
     *
     * @param postId    Post ID
     * @param commentId Comment ID
     * @param userId    댓글 작성자 ID
     * @param requestVO 수정할 댓글 정보
     */
    public abstract void modifyPostComment(Long postId, Long commentId, Long userId, CommentRequestVO requestVO);

    /**
     * 게시물 댓글 삭제
     *
     * @param postId    Post ID
     * @param commentId Comment ID
     * @param userId    댓글 작성자 ID
     */
    public abstract void removePostComment(Long postId, Long commentId, Long userId);

    /**
     * 게시물 댓글 리스트 조회
     *
     * @param postId    Post ID
     * @param requestVO 페이징 정보
     * @return
     */
    public abstract Page<CommentDto> readPostComments(Long postId, DataListRequestVO requestVO);

    /**
     * 게시물 상품 리스트 조회
     *
     * @param postId    Post ID
     * @param requestVO 페이징 정보
     * @return
     */
    public abstract Page<ProductDto> readPostProducts(Long postId, DataListRequestVO requestVO);
}
