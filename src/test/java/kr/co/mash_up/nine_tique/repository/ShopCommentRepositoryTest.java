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
import kr.co.mash_up.nine_tique.domain.Shop;
import kr.co.mash_up.nine_tique.domain.ShopComment;
import kr.co.mash_up.nine_tique.web.vo.DataListRequestVO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ethankim on 2017. 7. 31..
 */
/*
@DataJpaTest
    1. default로 in-memory embedded database를 사용하고,
    2. @Entity 클래스, jpa repository config를 scan
    3. @Component는 load되지 않는다
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ImportAutoConfiguration(classes = {PersistenceConfig.class})  // queryFactory 때문에
@ActiveProfiles(profiles = "test")
public class ShopCommentRepositoryTest {

    private static final int LOOP_COUNT = 25;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopCommentRepository shopCommentRepository;

    private Shop shop;

    private ShopComment comment;

    @Before
    public void setUp() throws Exception {
        shop = new Shop();
        shop.setName("shopName");
        shop.setDescription("shopDesc");
        shop.setPhoneNumber("shopPhoneNo");
        shop.setShopComments(new ArrayList<>());
        shop.setCommentCount(0L);
        shopRepository.save(shop);

        for (int i = 0; i < LOOP_COUNT; i++) {
            comment = new ShopComment();
            comment.setContents("commentContents" + i);
            comment.setShop(shop);
            shopCommentRepository.save(comment);
        }
    }

    @Test
    public void findOneByShopIdAndCommentId_매장_댓글_조회() throws Exception {
        // given : 매장 ID, 댓글 ID로
        long shopId = shop.getId();
        long commentId = comment.getId();

        // when : 매장의 댓글을 조회하면
        Optional<ShopComment> commentOp = shopCommentRepository.findOneByShopIdAndCommentId(shopId, commentId);

        // then : 매장의 댓글이 조회된다
        assertTrue(commentOp.isPresent());
    }

    @Test
    public void findShopComments_매장_댓글_리스트_조회() throws Exception {
        // given : 매장 ID, 페이지 번호, 사이즈로
        long shopId = shop.getId();
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 매장의 댓글 리스트를 페이징으로 조회하면
        Page<ShopComment> shopCommentPage = shopCommentRepository.findShopComments(shopId, pageable);

        // then : 매장의 댓글 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(shopCommentPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(shopCommentPage.getNumber(), pageNo);
        assertEquals(shopCommentPage.getTotalElements(), LOOP_COUNT);
    }
}
