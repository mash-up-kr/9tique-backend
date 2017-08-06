package kr.co.mash_up.nine_tique.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import kr.co.mash_up.nine_tique.config.PersistenceConfig;
import kr.co.mash_up.nine_tique.domain.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ethankim on 2017. 7. 31..
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ImportAutoConfiguration(classes = {PersistenceConfig.class})
@ActiveProfiles(profiles = "test")
public class ImageRepositoryTest {

    private static final String IMAGE_FILE_NAME = "fileName";

    @Autowired
    private ImageRepository imageRepository;

    @Before
    public void setUp() throws Exception {
        Image image = new Image();
        image.setFileName(IMAGE_FILE_NAME);
        image.setOriginalFileName("originalFileName");
        image.setSize(1024L);

        imageRepository.save(image);
    }

    @Test
    public void findByFileName_파일이름으로_이미지_조회() throws Exception {
        // given : 파일이름으로
        String fileName = IMAGE_FILE_NAME;

        // when : 이미지를 조회하면
        Optional<Image> imageOp = imageRepository.findByFileName(fileName);

        // then : 이미지가 조회된다
        assertTrue(imageOp.isPresent());
        assertEquals(imageOp.get().getFileName(), fileName);
    }
}
