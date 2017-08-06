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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.config.PersistenceConfig;
import kr.co.mash_up.nine_tique.domain.Post;
import kr.co.mash_up.nine_tique.domain.PostProduct;
import kr.co.mash_up.nine_tique.domain.Product;
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
public class PostRepositoryTest {

    private static final int LOOP_COUNT = 25;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProductRepository productRepository;

    private Post post;

    private List<Product> products;

    @Before
    public void setUp() throws Exception {

        products = new ArrayList<>();
        for (int i = 0; i < LOOP_COUNT; i++) {
            Product product = new Product();
            product.setName("name");
            product.setSize("size");
            product.setPrice(10000);
            product.setDescription("desc");
            product.setStatus(Product.Status.SELL);
            productRepository.save(product);
            products.add(product);
        }

        for (int i = 0; i < LOOP_COUNT; i++) {
            post = new Post();
            post.setName("postName");
            post.setContents("postContents");
            post.setPostComments(new ArrayList<>());
            post.setCommentCount(0L);
            post.setPostProducts(new ArrayList<>());
            postRepository.save(post);

            List<PostProduct> postProducts = products.stream()
                    .map(product -> new PostProduct(post, product))
                    .collect(Collectors.toList());

            post.setPostProducts(postProducts);
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

    @Test
    public void findPostProducts_게시물의_상품_리스트_조회() throws Exception {
        // given : 게시물 ID, 페이지 번호, 사이즈로
        long postId = post.getId();
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 게시물의 상품 리스트를 페이징으로 조회하면
        Page<PostProduct> postProducts = postRepository.findPostProducts(postId, pageable);

        // then : 게시물의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(postProducts.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(postProducts.getNumber(), pageNo);
        assertEquals(postProducts.getTotalElements(), LOOP_COUNT);
    }
}
