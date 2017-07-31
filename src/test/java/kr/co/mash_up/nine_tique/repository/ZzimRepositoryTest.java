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
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.domain.Zzim;
import kr.co.mash_up.nine_tique.domain.ZzimProduct;
import kr.co.mash_up.nine_tique.web.vo.DataListRequestVO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by ethankim on 2017. 7. 28..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
@DataJpaTest
@ActiveProfiles(profiles = "test")
public class ZzimRepositoryTest {

    private static final int LOOP_COUNT = 25;

    @Autowired
    private ZzimRepository zzimRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    private User user;

    private Product product;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setName("user");
        userRepository.save(user);

        Zzim zzim = new Zzim();
        zzim.setUser(user);
        zzim.setZzimProducts(new ArrayList<>());
        zzimRepository.save(zzim);

        for (int i = 0; i < LOOP_COUNT; i++) {
            product = new Product();
            product.setName("name");
            product.setSize("size");
            product.setPrice(10000);
            product.setDescription("desc");
            product.setStatus(Product.Status.SELL);
            productRepository.save(product);

            zzim.addZzimProduct(new ZzimProduct(zzim, product));
        }
    }

    @Test
    public void findZzimProduct_유저가_찜한_상품_조회() throws Exception {
        // given : 유저ID, 상품 ID로
        long userId = user.getId();
        long productId = product.getId();

        // when : 유저가 찜한 상품을 조회하면
        Optional<ZzimProduct> zzimProductOp = zzimRepository.findZzimProduct(userId, productId);

        // then : 찜한 상품이 조회된다
        assertThat(zzimProductOp.isPresent());

        ZzimProduct zzimProduct = zzimProductOp.get();
        assertThat(zzimProduct.getId().getProductId())
                .isEqualTo(productId);
    }

    @Test
    public void findZzimProducts_유저가_찜한_상품_리스트_페이징으로_조회() throws Exception {
        // given : 유저ID, 페이지 번호, 사이즈로
        long userId = user.getId();
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 유저가 찜한 상품 리스트를 페이징으로 조회하면
        Page<ZzimProduct> zzimProductPage = zzimRepository.findZzimProducts(userId, pageable);

        // then : 매장 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(zzimProductPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(zzimProductPage.getNumber(), pageNo);
        assertEquals(zzimProductPage.getTotalElements(), LOOP_COUNT);
    }

    @Test
    public void findZzimProducts_유저가_찜한_상품_리스트_조회() throws Exception {
        // given : 유저 ID로
        long userId = user.getId();

        // when : 유저가 찜한 상품 리스트를 조회하면
        List<ZzimProduct> zzimProducts = zzimRepository.findZzimProducts(userId);

        // then : 유저가 찜한 상품 리스트가 조회된다
        assertThat(zzimProducts)
                .isNotEmpty()
                .hasSize(LOOP_COUNT);
    }
}
