package kr.co.mash_up.domain;

import kr.co.mash_up.builder.CategoryBuilder;
import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
public class CategoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreated() {
        Category category = new CategoryBuilder()
                .withMain("main1")
                .withSub("sub1")
                .build();

        String main = category.getMain();

        // when
        Category afterCategory = categoryRepository.save(category);

        // then
        assertThat(main).isEqualTo(afterCategory.getMain());
    }
}
