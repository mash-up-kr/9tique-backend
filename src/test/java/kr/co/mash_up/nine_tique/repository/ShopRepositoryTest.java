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
import java.util.List;
import java.util.Optional;

import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.builder.ShopBuilder;
import kr.co.mash_up.nine_tique.domain.Shop;
import kr.co.mash_up.nine_tique.web.vo.DataListRequestVO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
@DataJpaTest
@ActiveProfiles(profiles = "test")
public class ShopRepositoryTest {

    private static final String SHOP_NAME = "name";

    private static final String SHOP_DESCRIPTION = "info";

    private static final String SHOP_PHONE_NO = "phone";

    private static final int LOOP_COUNT = 25;

    @Autowired
    private ShopRepository shopRepository;

    private Shop testShop;

    @Before
    public void setUp() {
        List<Shop> shops = new ArrayList<>();
        for (int i = 0; i < LOOP_COUNT; i++) {
            Shop shop = new ShopBuilder()
                    .name(SHOP_NAME + i)
                    .description(SHOP_DESCRIPTION + i)
                    .phoneNumber(SHOP_PHONE_NO + i)
                    .commentCount(0L)
                    .build();
            shops.add(shop);
        }

        testShop = shopRepository.save(shops).get(0);
    }

    @Test
    public void testFindByName_이름으로_매장_조회() throws Exception {
        // given : 매장 이름으로
        String shopName = testShop.getName();

        // when : 매장을 조회하면
        Shop foundShop = shopRepository.findByName(shopName);

        // then : 매장이 조회된다
        assertThat(shopName).isEqualTo(foundShop.getName());
    }

    @Test
    public void testFindByPhone_전화번호로_매장_조회() throws Exception {
        // given : 매장 전화번호로
        String shopPhoneNo = testShop.getPhoneNumber();

        // when : 매장을 조회하면
        Shop foundShop = shopRepository.findByPhoneNumber(shopPhoneNo);

        // then : 매장이 조회된다
        assertThat(shopPhoneNo).isEqualTo(foundShop.getPhoneNumber());
    }

    @Test
    public void testFindByNameAndPhone_이름과_전화번호로_매장_조회() throws Exception {
        // given : 매장 이름과 전화번호로
        String shopName = testShop.getName();
        String shopPhoneNo = testShop.getPhoneNumber();

        // when : 매장을 조회하면
        Optional<Shop> shopOp = shopRepository.findByNameAndPhoneNumber(shopName, shopPhoneNo);

        // then : 매장이 조회되고, 이름과 전화번호가 일치한다
        assertTrue(shopOp.isPresent());
        Shop shop = shopOp.get();

        assertThat(shopName).isEqualTo(shop.getName());
        assertThat(shopPhoneNo).isEqualTo(shop.getPhoneNumber());
    }

    @Test
    public void testFindOneByShopId_매장_조회() throws Exception {
        // given : 매장 ID로
        long shopId = testShop.getId();

        // when : 매장을 조회하면
        Optional<Shop> shopOp = shopRepository.findOneByShopId(shopId);

        // then : 매장이 조회된다
        assertTrue(shopOp.isPresent());
    }

    @Test
    public void testFindShops_매장_리스트_조회() throws Exception {
        // given : 페이지 번호, 페이지 사이즈로
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 매장 리스트를 페이징으로 조회하면
        Page<Shop> shopPage = shopRepository.findShops(pageable);

        // then : 매장 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(shopPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(shopPage.getNumber(), pageNo);
        assertEquals(shopPage.getTotalElements(), LOOP_COUNT);
    }
}
