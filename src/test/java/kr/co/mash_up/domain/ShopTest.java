package kr.co.mash_up.domain;

import kr.co.mash_up.builder.ShopBuilder;
import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.domain.Shop;
import kr.co.mash_up.nine_tique.repository.ShopRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
@ActiveProfiles(profiles = "test")
public class ShopTest {

    public static final String TEST_NAME = "name";
    public static final String TEST_INFO = "info";
    public static final String TEST_PHONE = "phone";

    @Autowired
    private ShopRepository shopRepository;

    private Shop testShop;

    @Before
    public void setup() {
        Shop shop = new ShopBuilder()
                .withName(TEST_NAME)
                .withInfo(TEST_INFO)
                .withPhone(TEST_PHONE)
                .withEnabled(true)
                .build();
        testShop = shopRepository.save(shop);
    }

    @After
    public void tearDown() {
        shopRepository.delete(testShop.getId());
    }

    @Test
    public void testFindByName() {
        // when
        Shop findShop = shopRepository.findByName(TEST_NAME);

        // then
        assertThat(TEST_NAME).isEqualTo(findShop.getName());
    }

    @Test
    public void testFindByPhone() {
        // when
        Shop findShop = shopRepository.findByPhone(TEST_PHONE);

        // then
        assertThat(TEST_PHONE).isEqualTo(findShop.getPhone());
    }

    @Test
    public void testFindByNameAndPhone() {
        // when
        Shop findShop = shopRepository.findByNameAndPhone(TEST_NAME, TEST_PHONE);

        // then
        assertThat(TEST_NAME).isEqualTo(findShop.getName());
        assertThat(TEST_PHONE).isEqualTo(findShop.getPhone());
    }
}
