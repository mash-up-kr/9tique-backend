package kr.co.mash_up.nine_tique.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.domain.Brand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ethankim on 2017. 7. 30..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
@DataJpaTest
@ActiveProfiles(profiles = "test")
public class BrandRepositoryTest {

    private static final String BRAND_NAME_KO = "브랜드 이름";

    private static final String BRAND_NAME_ENG = "nameEng";

    @Autowired
    private BrandRepository brandRepository;

    @Before
    public void setUp() throws Exception {
        Brand brand = new Brand();
        brand.setNameEng(BRAND_NAME_ENG);
        brand.setNameKo(BRAND_NAME_KO);
        brandRepository.save(brand);
    }

    @Test
    public void findByNameKo_한글_이름으로_브랜드_조회() throws Exception {
        // given : 한글 브랜드 이름으로
        String brandNameKo = BRAND_NAME_KO;

        // when : 브랜드를 조회하면
        Optional<Brand> brandOp = brandRepository.findByNameKo(brandNameKo);

        // then : 브랜드가 조회된다
        assertTrue(brandOp.isPresent());
        assertEquals(brandOp.get().getNameKo(), brandNameKo);
    }

    @Test
    public void findByNameEng_영어_이름으로_브랜드_조회() throws Exception {
        // given : 영어 브랜드 이름으로
        String brandNameEng = BRAND_NAME_ENG;

        // when : 브랜드를 조회하면
        Optional<Brand> brandOp = brandRepository.findByNameEng(brandNameEng);

        // then : 브랜드가 조회된다
        assertTrue(brandOp.isPresent());
        assertEquals(brandOp.get().getNameEng(), brandNameEng);
    }
}
