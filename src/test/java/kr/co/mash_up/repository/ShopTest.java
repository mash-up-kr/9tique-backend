package kr.co.mash_up.repository;

import kr.co.mash_up.builder.ShopBuilder;
import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.domain.Shop;
import kr.co.mash_up.nine_tique.repository.ShopRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
@DataJpaTest
@ActiveProfiles(profiles = "test")
public class ShopTest {

    private static final String TEST_NAME = "name";
    private static final String TEST_DESCRIPTION = "info";
    private static final String TEST_PHONE = "phone";

    @Autowired
    private ShopRepository shopRepository;

    private Shop testShop;

    @Before
    public void setup() {
        Shop shop = new ShopBuilder()
                .withName(TEST_NAME)
                .withDescription(TEST_DESCRIPTION)
                .withPhoneNumber(TEST_PHONE)
                .withCommentCount(0L)
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
        Shop findShop = shopRepository.findByPhoneNumber(TEST_PHONE);

        // then
        assertThat(TEST_PHONE).isEqualTo(findShop.getPhoneNumber());
    }

    @Test
    public void testFindByNameAndPhone() {
        // when
        Optional<Shop> shopOptional = shopRepository.findByNameAndPhoneNumber(TEST_NAME, TEST_PHONE);

        Shop findShop = shopOptional.get();

        // then
        assertThat(TEST_NAME).isEqualTo(findShop.getName());
        assertThat(TEST_PHONE).isEqualTo(findShop.getPhoneNumber());
    }
}
