package kr.co.mash_up.nine_tique.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import kr.co.mash_up.nine_tique.config.PersistenceConfig;
import kr.co.mash_up.nine_tique.domain.Post;
import kr.co.mash_up.nine_tique.domain.PostComment;
import kr.co.mash_up.nine_tique.web.vo.DataListRequestVO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ethankim on 2017. 7. 31..
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ImportAutoConfiguration(classes = {PersistenceConfig.class})
@ActiveProfiles(profiles = "test")
public class PostCommentRepositoryTest {

    private static final int LOOP_COUNT = 25;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    private Post post;

    private PostComment comment;

    @Before
    public void setUp() throws Exception {
        post = new Post();
        post.setName("postName");
        post.setContents("postContents");
        post.setPostComments(new ArrayList<>());
        post.setCommentCount(0L);
        postRepository.save(post);

        for (int i = 0; i < LOOP_COUNT; i++) {
            comment = new PostComment();
            comment.setContents("commentContents" + i);
            comment.setPost(post);
            postCommentRepository.save(comment);
        }
    }

    @Test
    public void findOneByPostIdAndCommentId_게시물_댓글_조회() throws Exception {
        // given : 게시물 ID, 댓글 ID로
        long postId = post.getId();
        long commentId = comment.getId();

        // when : 게시물의 댓글을 조회하면
        Optional<PostComment> postCommentOp = postCommentRepository.findOneByPostIdAndCommentId(postId, commentId);

        // then : 게시물의 댓글이 조회된다
        assertTrue(postCommentOp.isPresent());
    }

    @Test
    public void findPostComments_게시물_댓글리스트_조회() throws Exception {
        // given : 게시물 ID, 페이지 번호, 사이즈로
        long postId = post.getId();
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 게시물의 댓글 리스트를 페이징으로 조회하면
        Page<PostComment> postCommentPage = postCommentRepository.findPostComments(postId, pageable);

        // then : 게시물의 댓글 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(postCommentPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(postCommentPage.getNumber(), pageNo);
        assertEquals(postCommentPage.getTotalElements(), LOOP_COUNT);
    }
}
