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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kr.co.mash_up.nine_tique.config.PersistenceConfig;
import kr.co.mash_up.nine_tique.domain.Brand;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Post;
import kr.co.mash_up.nine_tique.domain.PostProduct;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.Promotion;
import kr.co.mash_up.nine_tique.domain.PromotionProduct;
import kr.co.mash_up.nine_tique.domain.Shop;
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
public class ProductRepositoryTest {

    private static final int LOOP_COUNT = 25;

    private static final String MAIN_CATEGORY = "mainCategory";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private BrandRepository brandRepository;

    private Product product;

    private Category testCategory;

    private Post testPost;

    private Shop testShop;

    private Promotion testPromotion;

    private Brand testBrand;

    @Before
    public void setUp() throws Exception {
        // 카테고리
        testCategory = new Category();
        testCategory.setMain(MAIN_CATEGORY);
        testCategory.setSub("sub");
        categoryRepository.save(testCategory);

        // 게시물
        testPost = new Post();
        testPost.setName("postName");
        testPost.setContents("postContents");
        testPost.setPostComments(new ArrayList<>());
        testPost.setCommentCount(0L);
        testPost.setPostProducts(new ArrayList<>());
        postRepository.save(testPost);

        // 매장
        testShop = new Shop();
        testShop.setName("shopName");
        testShop.setDescription("shopDesc");
        testShop.setPhoneNumber("shopPhoneNo");
        testShop.setCommentCount(0L);
        shopRepository.save(testShop);

        // 프로모션
        testPromotion = new Promotion();
        testPromotion.setName("name");
        testPromotion.setDescription("desc");
        testPromotion.setStartAt(LocalDateTime.now());
        testPromotion.setEndAt(LocalDateTime.now().plusDays(3L));
        testPromotion.setPriority(1000);
        testPromotion.setStatus(Promotion.Status.SAVED);
        testPromotion.setPromotionProducts(new ArrayList<>());
        promotionRepository.save(testPromotion);

        // 브랜드
        testBrand = new Brand();
        testBrand.setNameEng("nameEng");
        testBrand.setNameKo("브랜드 이름");
        brandRepository.save(testBrand);

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < LOOP_COUNT; i++) {
            product = new Product();
            product.setName("name");
            product.setSize("size");
            product.setPrice(10000);
            product.setDescription("desc");
            product.setStatus(Product.Status.SELL);
            product.setCategory(testCategory);
            product.setShop(testShop);
            product.setBrand(testBrand);
            productRepository.save(product);
            products.add(product);

            testPost.addProduct(new PostProduct(testPost, product));
            testPromotion.addProduct(new PromotionProduct(testPromotion, product));
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
    public void findProducts_상품_리스트_조회() throws Exception {
        // given : 페이지 번호, 사이즈로
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 상품 리스트를 페이징으로 조회하면
        Page<Product> productPage = productRepository.findProducts(pageable);

        // then : 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(productPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(productPage.getNumber(), pageNo);
        assertEquals(productPage.getTotalElements(), LOOP_COUNT);
    }

    @Test
    public void findProductsByCategory_카테고리별_상품_리스트_조회() throws Exception {
        // given : 카테고리, 페이지 번호, 사이즈로
        Category category = testCategory;
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 카테고리에 속한 상품 리스트를 페이징으로 조회하면
        Page<Product> productPage = productRepository.findProductsByCategory(category, pageable);


        // then : 카테고리에 속한 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(productPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(productPage.getNumber(), pageNo);
        assertEquals(productPage.getTotalElements(), LOOP_COUNT);
    }

    @Test
    public void findProductsByMainCategory_메인_카테고리별_상품_리스트_조회() throws Exception {
        // given : 메인 카테고리, 페이지 번호, 사이즈로
        String mainCategory = MAIN_CATEGORY;
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 메인 카테고리의 상품 리스트를 페이징으로 조회하면
        Page<Product> productPage = productRepository.findProductsByMainCategory(mainCategory, pageable);

        // then : 메인 카테고리의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(productPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(productPage.getNumber(), pageNo);
        assertEquals(productPage.getTotalElements(), LOOP_COUNT);
    }

    @Test
    public void findPostProducts_게시물의_상품_리스트_조회() throws Exception {
        // given : 게시물 ID, 페이지 번호, 사이즈로
        long postId = testPost.getId();
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 게시물의 상품 리스트를 페이징으로 조회하면
        Page<Product> postProducts = productRepository.findPostProducts(postId, pageable);

        // then : 게시물의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(postProducts.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(postProducts.getNumber(), pageNo);
        assertEquals(postProducts.getTotalElements(), LOOP_COUNT);
    }

    @Test
    public void findShopProducts_매장의_상품_리스트_조회() throws Exception {
        // given : 매장 ID, 페이지 번호, 사이즈로
        long shopId = testShop.getId();
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 매장의 상품 리스트를 페이징으로 조회하면
        Page<Product> shopProducts = productRepository.findShopProducts(shopId, pageable);

        // then : 매장의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(shopProducts.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(shopProducts.getNumber(), pageNo);
        assertEquals(shopProducts.getTotalElements(), LOOP_COUNT);

        for (Product product : shopProducts.getContent()) {
            assertEquals(product.getShop().getId().longValue(), shopId);
        }
    }

    @Test
    public void findShopProductsByCategory_카테고리별_매장의_상품_리스트_조회() throws Exception {
        // given : 매장 ID, 카테고리, 페이지 번호, 사이즈로
        long shopId = testShop.getId();
        Category category = testCategory;
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 카테고리별 매장의 상품 리스트를 페이징으로 조회하면
        Page<Product> productPage = productRepository.findShopProductsByCategory(shopId, category, pageable);

        // then : 카테고리별 매장의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(productPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(productPage.getNumber(), pageNo);
        assertEquals(productPage.getTotalElements(), LOOP_COUNT);

        for (Product product : productPage.getContent()) {
            assertEquals(product.getShop().getId().longValue(), shopId);
            assertEquals(product.getCategory(), category);
        }
    }

    @Test
    public void findShopProductsByMainCategory_메인_카테고리별_매장의_상품_리스트_조회() throws Exception {
        // given : 매장 ID, 메인 카테고리, 페이지 번호, 사이즈로
        long shopId = testShop.getId();
        String mainCategory = MAIN_CATEGORY;
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 메인 카테고리별 프로모션의 상품 리스트를 페이징으로 조회하면
        Page<Product> productPage = productRepository.findShopProductsByMainCategory(shopId, mainCategory, pageable);

        // then : 메인 카테고리별 프로모션의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(productPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(productPage.getNumber(), pageNo);
        assertEquals(productPage.getTotalElements(), LOOP_COUNT);

        for (Product product : productPage.getContent()) {
            assertEquals(product.getShop().getId().longValue(), shopId);
            assertEquals(product.getCategory().getMain(), mainCategory);
        }
    }

    @Test
    public void findPromotionProducts_프로모션의_상품_리스트_조회() throws Exception {
        // given : 프로모션 ID, 페이지 번호, 사이즈로
        long promotionId = testPromotion.getId();
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 프로모션의 상품 리스트를 페이징으로 조회하면
        Page<Product> promotionProducts = productRepository.findPromotionProducts(promotionId, pageable);

        // then : 프로모션의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(promotionProducts.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(promotionProducts.getNumber(), pageNo);
        assertEquals(promotionProducts.getTotalElements(), LOOP_COUNT);
    }

    @Test
    public void findPromotionProductsByCategory_카테고리별_프로모션의_상품_리스트_조회() throws Exception {
        // given : 프로모션 ID, 카테고리, 페이지 번호, 사이즈로
        long promotionId = testPromotion.getId();
        Category category = testCategory;
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 카테고리별 프로모션의 상품 리스트를 페이징으로 조회하면
        Page<Product> productPage = productRepository.findPromotionProductsByCategory(promotionId, category, pageable);

        // then : 카테고리별 프로모션의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(productPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(productPage.getNumber(), pageNo);
        assertEquals(productPage.getTotalElements(), LOOP_COUNT);

        for (Product product : productPage.getContent()) {
            assertEquals(product.getCategory(), category);
        }
    }

    @Test
    public void findPromotionProductsByMainCategory_메인_카테고리별_프로모션의_상품_리스트_조회() throws Exception {
        // given : 프로모션 ID, 메인 카테고리, 페이지 번호, 사이즈로
        long promotionId = testPromotion.getId();
        String mainCategory = MAIN_CATEGORY;
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 메인 카테고리별 프로모션의 상품 리스트를 페이징으로 조회하면
        Page<Product> productPage = productRepository.findPromotionProductsByMainCategory(promotionId, mainCategory, pageable);

        // then : 메인 카테고리별 프로모션의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(productPage.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(productPage.getNumber(), pageNo);
        assertEquals(productPage.getTotalElements(), LOOP_COUNT);

        for (Product product : productPage.getContent()) {
            assertEquals(product.getCategory().getMain(), mainCategory);
        }
    }

    @Test
    public void findBrandProducts_브랜드의_상품_리스트_조회() throws Exception {
        // given : 브랜드 ID, 페이지 번호, 사이즈로
        long brandId = testBrand.getId();
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 브랜드의 상품 리스트를 페이징으로 조회하면
        Page<Product> brandProducts = productRepository.findBrandProducts(brandId, pageable);

        // then : 브랜드의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(brandProducts.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(brandProducts.getNumber(), pageNo);
        assertEquals(brandProducts.getTotalElements(), LOOP_COUNT);

        for (Product product : brandProducts.getContent()) {
            assertEquals(product.getBrand().getId().longValue(), brandId);
        }
    }

    @Test
    public void findBrandProductsByCategory_카테고리별_브랜드의_상품_리스트_조회() throws Exception {
        // given : 브랜드 ID, 카테고리, 페이지 번호, 사이즈로
        long brandId = testBrand.getId();
        Category category = testCategory;
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 카테고리별 브랜드의 상품 리스트를 페이징으로 조회하면
        Page<Product> brandProducts = productRepository.findBrandProductsByCategory(brandId, category, pageable);

        // then : 카테고리별 브랜드의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(brandProducts.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(brandProducts.getNumber(), pageNo);
        assertEquals(brandProducts.getTotalElements(), LOOP_COUNT);

        for (Product product : brandProducts.getContent()) {
            assertEquals(product.getBrand().getId().longValue(), brandId);
            assertEquals(product.getCategory(), category);
        }
    }

    @Test
    public void findBrandProductsByMainCategory_메인_카테고리별_브랜드의_상품_리스트_조회() throws Exception {
        // given : 브랜드 ID, 메인 카테고리, 페이지 번호, 사이즈로
        long brandId = testBrand.getId();
        String mainCategory = MAIN_CATEGORY;
        int pageNo = 0;
        int pageSize = DataListRequestVO.DEFAULT_PAGE_ROW;

        Pageable pageable = new PageRequest(pageNo, pageSize);

        // when : 메인 카테고리별 브랜드의 상품 리스트를 페이징으로 조회하면
        Page<Product> brandProducts = productRepository.findBrandProductsByMainCategory(brandId, mainCategory, pageable);

        // then : 메인 카테고리별 브랜드의 상품 리스트 페이지가 페이지 사이즈만큼 조회된다
        assertThat(brandProducts.getContent())
                .isNotEmpty()
                .hasSize(pageSize);

        assertEquals(brandProducts.getNumber(), pageNo);
        assertEquals(brandProducts.getTotalElements(), LOOP_COUNT);

        for (Product product : brandProducts.getContent()) {
            assertEquals(product.getBrand().getId().longValue(), brandId);
            assertEquals(product.getCategory().getMain(), mainCategory);
        }
    }
}
