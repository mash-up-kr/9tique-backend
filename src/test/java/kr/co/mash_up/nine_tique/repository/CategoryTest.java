package kr.co.mash_up.nine_tique.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.builder.CategoryBuilder;
import kr.co.mash_up.nine_tique.domain.Category;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
@DataJpaTest
@ActiveProfiles(profiles = "test")
public class CategoryTest {

    public static final String TEST_MAIN = "testMain";

    public static final String TEST_SUB = "testSub";

    @Autowired
    private CategoryRepository categoryRepository;

    private List<Category> testCategories;

    @Before
    public void setup() {
        testCategories = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Category testCategory = new CategoryBuilder()
                    .withMain(TEST_MAIN)
                    .withSub(TEST_SUB + i)
                    .build();
            categoryRepository.save(testCategory);
            testCategories.add(testCategory);
        }
    }

    @After
    public void tearDown() {
        for (Category category : testCategories) {
            categoryRepository.delete(category.getId());
        }
        testCategories = null;
    }

    @Test
    public void testCreated() {
        Category category = testCategories.get(0);
        // then
        assertThat(TEST_MAIN).isEqualTo(category.getMain());
        assertThat(TEST_SUB + 0).isEqualTo(category.getSub());

    }

    @Test
    public void testFindByMainAndSub() {
        // when
        Optional<Category> categoryOp = categoryRepository.findOneByMainAndSub(TEST_MAIN, TEST_SUB + 0);
        Category oldCategory = categoryOp.get();

        // then
        assertThat(TEST_MAIN).isEqualTo(oldCategory.getMain());
        assertThat(TEST_SUB + 0).isEqualTo(oldCategory.getSub());
    }

    @Test
    public void testFindAll() {
        // when
        List<Category> categories = categoryRepository.findAll();

        // then
        for (Category category : categories) {

        }
    }

    //Todo: 수정하기
//    @Test
//    public void testFindByMain() {
//        // when
//        List<Category> categories = categoryRepository.findByMain(TEST_MAIN);
//
//        Category category;
//        for (int i = 0; i < categories.size(); i++) {
//            category = categories.get(i);
//            assertThat(category.getMain()).isEqualTo(TEST_MAIN);
//            assertThat(category.getSub()).isEqualTo(TEST_SUB + i);
//            assertThat(category.isEnabled()).isTrue();
//        }
//    }

    @Test
    public void testFindOneById() {
        // when
        Optional<Category> categoryOp = categoryRepository.findOneByCategoryId(testCategories.get(0).getId());

        // then

    }

    @Test
    public void testDelete() {

        // when
        Category category = categoryRepository.findOne(testCategories.get(0).getId());


        categoryRepository.save(category);
        Category findCategory = categoryRepository.findOne(testCategories.get(0).getId());

        // then
    }
}
