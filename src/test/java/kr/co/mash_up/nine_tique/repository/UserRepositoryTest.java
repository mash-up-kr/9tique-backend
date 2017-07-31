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
import kr.co.mash_up.nine_tique.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ethankim on 2017. 7. 31..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
@DataJpaTest
@ActiveProfiles(profiles = "test")
public class UserRepositoryTest {

    private static final String USER_NAME = "userName";

    private static final String USER_EMAIL = "userEmail";

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setName(USER_NAME);
        user.setEmail(USER_EMAIL);
        userRepository.save(user);
    }

    @Test
    public void findByName_이름으로_유저_조회() throws Exception {
        // given : 이름으로
        String userName = USER_NAME;

        // when : 유저를 조회하면
        User user = userRepository.findByName(userName);

        // then : 유저가 조회된다
        assertNotNull(user);
        assertEquals(user.getName(), userName);
    }

    @Test
    public void findByEmail_이메일로_유저_조회() throws Exception {
        // given : 이메일로
        String userEmail = USER_EMAIL;

        // when : 유저를 조회하면
        Optional<User> userOp = userRepository.findByEmail(userEmail);

        // then : 유저가 조회된다
        assertTrue(userOp.isPresent());

        User user = userOp.get();
        assertEquals(user.getEmail(), userEmail);
    }

    @Test
    public void findOneById_유저_조회() throws Exception {
        // given : 유저 ID로
        long userId = user.getId();

        // when : 유저를 조회하면
        Optional<User> userOp = userRepository.findOneById(userId);

        // then : 유저가 조회된다
        assertTrue(userOp.isPresent());
        assertThat(userOp.get().getId())
                .isEqualTo(userId);
    }
}
