package kr.co.mash_up.service;

import kr.co.mash_up.TestWithContext;
import kr.co.mash_up.builder.CategoryBuilder;
import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.service.CategorySservice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class CategoryServiceTest extends TestWithContext {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategorySservice categorySservice;

    private Category testCategory;

    @Before
    public void setup() {
        testCategory = new CategoryBuilder()
                .withMain("main1")
                .withSub("sub1")
                .build();
//        categoryRepository.save(testCategory);
    }

    @After
    public void tearDown() {
        categoryRepository.delete(testCategory.getId());
    }

    @Test
    public void testCreate() {
        int beforeSize = categorySservice.findCategories().size();

        categorySservice.create(testCategory);

        Category category = categoryRepository.findOne(testCategory.getId());
        assertThat(category.getMain(), is("main1"));
        assertThat(category.getSub(), is("sub1"));

        int afterSize = categorySservice.findCategories().size();

        assertThat(afterSize, is(beforeSize + 1));
    }

    @Test
    public void testDelete(){
        // Todo:
    }

    @Test
    public void testFindOne(){
        // Todo:
    }

    @Test
    public void testUpdate(){
        // Todo:
    }

    @Test
    public void testFindCategories(){
        // Todo:
    }
}
