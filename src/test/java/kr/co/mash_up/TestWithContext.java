package kr.co.mash_up;

import kr.co.mash_up.nine_tique.NineTiqueApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test Context를 다른 클래스에 상속하기 위함
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
//@ActiveProfiles(profiles = "test")
public class TestWithContext {
}
