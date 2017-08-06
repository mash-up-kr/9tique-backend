package kr.co.mash_up.nine_tique.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import kr.co.mash_up.nine_tique.builder.CategoryBuilder;
import kr.co.mash_up.nine_tique.config.PersistenceConfig;
import kr.co.mash_up.nine_tique.domain.Category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ImportAutoConfiguration(classes = {PersistenceConfig.class})
@ActiveProfiles(profiles = "test")
public class CategoryRepositoryTest {

    private static final int LOOP_COUNT = 25;

    private static final String TEST_MAIN = "testMain";

    private static final String TEST_SUB = "testSub";

    @Autowired
    private CategoryRepository categoryRepository;

    private Category testCategory;

    @Before
    public void setup() {
        for (int i = 0; i < LOOP_COUNT; i++) {
            testCategory = new CategoryBuilder()
                    .withMain(TEST_MAIN)
                    .withSub(TEST_SUB + i)
                    .build();
            categoryRepository.save(testCategory);
        }
    }

    @Test
    public void findOneByMainAndSub_메인_하위_카테고리_이름으로_조회() {
        // given : 메인 카테고리, 하위 카테고리 이름으로
        String mainCategoryName = TEST_MAIN;
        String subCategoryName = testCategory.getSub();

        // when : 카테고리를 조회하면
        Optional<Category> categoryOp = categoryRepository.findOneByMainAndSub(mainCategoryName, subCategoryName);

        // then : 카테고리가 조회된다
        assertTrue(categoryOp.isPresent());

        Category category = categoryOp.get();
        assertEquals(category.getMain(), mainCategoryName);
        assertEquals(category.getSub(), subCategoryName);
    }

    @Test
    public void findByMain_메인_카테고리_이름으로_조회() {
        // given : 메인 카테고리 이름으로
        String mainCategoryName = TEST_MAIN;

        // when : 메인 카테고리의 하위 카테고리 리스트를 조회하면
        List<Category> categories = categoryRepository.findByMain(TEST_MAIN);

        // then : 하위 카테고리 리스트가 조회된다
        assertThat(categories)
                .isNotEmpty();

        for (Category category : categories) {
            assertEquals(category.getMain(), mainCategoryName);
        }
    }

    @Test
    public void findOneByCategoryId_카테고리_ID로_조회() {
        // given : 카테고리 ID로
        long categoryId = testCategory.getId();

        // when : 카테고리를 조회하면
        Optional<Category> categoryOp = categoryRepository.findOneByCategoryId(categoryId);

        // then : 카테고리가 조회된다
        assertTrue(categoryOp.isPresent());

        Category category = categoryOp.get();
        assertThat(category.getId()).isEqualTo(categoryId);
    }
}
