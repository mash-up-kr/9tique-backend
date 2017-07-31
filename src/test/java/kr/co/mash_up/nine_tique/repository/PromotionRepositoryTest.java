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

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.domain.Promotion;
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
public class PromotionRepositoryTest {

    private static final int LOOP_COUNT = 25;

    @Autowired
    private PromotionRepository promotionRepository;

    private Promotion promotion;

    @Before
    public void setUp() throws Exception {

        for (int i = 0; i < LOOP_COUNT; i++) {
            promotion = new Promotion();
            promotion.setName("name");
            promotion.setDescription("desc");
            promotion.setStartAt(LocalDateTime.now());
            promotion.setEndAt(LocalDateTime.now().plusDays(3L));
            promotion.setPriority(1000);
            promotion.setStatus(Promotion.Status.SAVED);
            promotionRepository.save(promotion);
        }
    }

    @Test
    public void findOneByPromotionId_프로모션_조회() throws Exception {
        // given : 프로모션 ID로
        long promotionId = promotion.getId();

        // when : 프로모션을 조회하면
        Optional<Promotion> promotionOp = promotionRepository.findOneByPromotionId(promotionId);

        // then : 프로모션이 조회된다
        assertTrue(promotionOp.isPresent());
    }

    @Test
    public void findPromotions_프로모션_리스트_조회() throws Exception {
        // given : 페이지 번호, 페이지 사이즈로
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 프로모션 리스트를 페이징으로 조회하면
        Page<Promotion> promotionPage = promotionRepository.findPromotions(pageable);

        // then : 프로모션 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(promotionPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(promotionPage.getNumber(), pageNo);
        assertEquals(promotionPage.getTotalElements(), LOOP_COUNT);
    }
}
