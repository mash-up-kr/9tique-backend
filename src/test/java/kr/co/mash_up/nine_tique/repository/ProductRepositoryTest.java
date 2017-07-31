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

import java.util.Optional;

import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Product;
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
public class ProductRepositoryTest {

    private static final int LOOP_COUNT = 25;

    private static final String MAIN_CATEGORY = "mainCategory";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Product product;

    private Category testCategory;

    @Before
    public void setUp() throws Exception {
        testCategory = new Category();
        testCategory.setMain(MAIN_CATEGORY);
        testCategory.setSub("sub");
        categoryRepository.save(testCategory);

        for (int i = 0; i < LOOP_COUNT; i++) {
            product = new Product();
            product.setName("name");
            product.setSize("size");
            product.setPrice(10000);
            product.setDescription("desc");
            product.setStatus(Product.Status.SELL);
            product.setCategory(testCategory);
            productRepository.save(product);
        }
    }

    @Test
    public void findOneByProductId() throws Exception {
        // given : 상품 ID로
        long productId = product.getId();

        // when : 상품을 조회하면
        Optional<Product> productOp = productRepository.findOneByProductId(productId);

        // then : 상품이 조회된다
        assertTrue(productOp.isPresent());
    }

    @Test
    public void findAll() throws Exception {
        // given : 페이지 번호, 사이즈로
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 상품 리스트를 페이징으로 조회하면
        Page<Product> productPage = productRepository.findAll(pageable);

        // then : 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(productPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(productPage.getNumber(), pageNo);
        assertEquals(productPage.getTotalElements(), LOOP_COUNT);
    }

    @Test
    public void findByCategory() throws Exception {
        // given : 카테고리, 페이지 번호, 사이즈로
        Category category = testCategory;
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 카테고리에 속한 상품 리스트를 페이징으로 조회하면
        Page<Product> productPage = productRepository.findByCategory(pageable, category);


        // then : 카테고리에 속한 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(productPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(productPage.getNumber(), pageNo);
        assertEquals(productPage.getTotalElements(), LOOP_COUNT);
    }

    @Test
    public void findByMainCategory() throws Exception {
        // given : 메인 카테고리, 페이지 번호, 사이즈로
        String mainCategory = MAIN_CATEGORY;
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 메인 카테고리의 상품 리스트를 페이징으로 조회하면
        Page<Product> productPage = productRepository.findByMainCategory(pageable, mainCategory);

        // then : 메인 카테고리의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(productPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(productPage.getNumber(), pageNo);
        assertEquals(productPage.getTotalElements(), LOOP_COUNT);
    }
}
