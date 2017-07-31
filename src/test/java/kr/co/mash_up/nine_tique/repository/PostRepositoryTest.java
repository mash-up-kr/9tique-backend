package kr.co.mash_up.nine_tique.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.domain.Post;
import kr.co.mash_up.nine_tique.web.vo.DataListRequestVO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ethankim on 2017. 7. 31..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
@DataJpaTest
@ActiveProfiles(profiles = "test")
public class PostRepositoryTest {

    private static final int LOOP_COUNT = 25;

    @Autowired
    private PostRepository postRepository;

    private Post post;

    @Before
    public void setUp() throws Exception {

        for (int i = 0; i < LOOP_COUNT; i++) {
            post = new Post();
            post.setName("postName");
            post.setContents("postContents");
            post.setPostComments(new ArrayList<>());
            post.setCommentCount(0L);
            postRepository.save(post);
        }
    }

    @Test
    public void findOneByPostId_게시물_조회() throws Exception {
        // given : 게시물 ID로
        long postId = post.getId();

        // when : 게시물을 조회하면
        Optional<Post> postOp = postRepository.findOneByPostId(postId);

        // then : 게시물이 조회된다
        assertTrue(postOp.isPresent());
    }

    @Test
    public void findPosts_게시물_리스트_조회() throws Exception {
        // given : 페이지 번호, 사이즈로
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 게시물 리스트를 페이징으로 조회하면
        Page<Post> postPage = postRepository.findPosts(pageable);

        // then : 게시물 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(postPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(postPage.getNumber(), pageNo);
        assertEquals(postPage.getTotalElements(), LOOP_COUNT);
    }
}
