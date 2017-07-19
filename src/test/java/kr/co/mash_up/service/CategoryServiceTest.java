package kr.co.mash_up.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.mash_up.builder.CategoryBuilder;
import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.service.CategoryService;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
@ActiveProfiles(profiles = "test")
public class CategoryServiceTest {

    public static final String TEST_MAIN = "testMain";
    public static final String TEST_SUB = "testSub";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    private Category testCategory;

    @Before
    public void setup() {
        testCategory = new CategoryBuilder()
                .withMain(TEST_MAIN)
                .withSub(TEST_SUB)
                .withEnable(true)
                .build();
        categoryRepository.save(testCategory);
    }

    @After
    public void tearDown() {
        categoryRepository.delete(testCategory.getId());
    }

    @Test
    public void testCreate() {
        int beforeSize = categoryService.findCategories().size();

        Category category = new CategoryBuilder()
                .withMain(TEST_MAIN)
                .withSub(TEST_SUB + 1)
                .withEnable(true)
                .build();
        categoryRepository.save(category);

        int afterSize = categoryService.findCategories().size();

        assertThat(afterSize, is(beforeSize + 1));
    }

//    @Test
//    public void testDelete(){
//        // Todo:
//    }
//
//    @Test
//    public void testFindOne(){
//        // Todo:
//    }
//
//    @Test
//    public void testUpdate(){
//        // Todo:
//    }
//
//    @Test
//    public void testFindCategories(){
//        // Todo:
//    }
}
