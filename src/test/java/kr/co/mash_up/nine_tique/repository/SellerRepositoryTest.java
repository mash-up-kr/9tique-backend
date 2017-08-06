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

import kr.co.mash_up.nine_tique.config.PersistenceConfig;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.Seller;
import kr.co.mash_up.nine_tique.domain.SellerProduct;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.web.vo.DataListRequestVO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by ethankim on 2017. 7. 30..
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ImportAutoConfiguration(classes = {PersistenceConfig.class})
@ActiveProfiles(profiles = "test")
public class SellerRepositoryTest {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    private static final String AUTHENTI_CODE = "testFdes";

    private static final int LOOP_COUNT = 25;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        userRepository.save(user);

        Seller seller = new Seller();
        seller.setAuthentiCode(AUTHENTI_CODE);
        seller.setUser(user);
        seller.setSellerProducts(new ArrayList<>());
        sellerRepository.save(seller);

        for (int i = 0; i < LOOP_COUNT; i++) {
            Product product = new Product();
            product.setName("name");
            product.setDescription("description");
            product.setSize("size");
            product.setPrice(1000);
            product.setStatus(Product.Status.SELL);
            productRepository.save(product);

            seller.addSellerProduct(new SellerProduct(seller, product));
        }
        sellerRepository.save(seller);
    }

    @Test
    public void findByAuthentiCode_인증코드로_판매자_조회() throws Exception {
        // given : 인증코드로
        String authentiCode = AUTHENTI_CODE;

        // when : 판매자를 조회하면
        Optional<Seller> sellerOp = sellerRepository.findByAuthentiCode(authentiCode);

        // then : 판매자가 조회된다
        assertTrue(sellerOp.isPresent());
    }

    @Test
    public void findOneByUserId_유저ID로_판매자_조회() throws Exception {
        // given : 유저 ID로
        long userId = user.getId();

        // when : 판매자를 조회하면
        Optional<Seller> sellerOp = sellerRepository.findOneByUserId(userId);

        // then : 판매자가 조회된다
        assertTrue(sellerOp.isPresent());
    }

    @Test
    public void findSellerProducts_판매자의_상품_리스트_조회() throws Exception {
        // given : 유저 ID로
        long userId = user.getId();

        // when : 판매자의 상품 리스트를 조회하면
        List<SellerProduct> sellerProducts = sellerRepository.findSellerProducts(userId);

        // then : 상품 리스트가 조회된다
        assertThat(sellerProducts)
                .isNotEmpty()
                .hasSize(LOOP_COUNT);
    }

    @Test
    public void findSellerProducts_판매자의_상품_리스트_조회_페이징() throws Exception {
        // given : 유저 ID, 페이지 번호, 페이지 사이즈로
        long userId = user.getId();
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 판매자의 상품 리스트를 페이징으로 조회하면
        Page<SellerProduct> sellerProductsPage = sellerRepository.findSellerProducts(userId, pageable);

        // then : 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(sellerProductsPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertThat(sellerProductsPage.getNumber())
                .isEqualTo(pageNo);
    }
}
