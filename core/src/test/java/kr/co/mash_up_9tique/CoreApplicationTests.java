package kr.co.mash_up_9tique;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication  // test시 context load를 할 수 있게 설정
public class CoreApplicationTests {

    @Test
    public void contextLoads() {
    }

}
