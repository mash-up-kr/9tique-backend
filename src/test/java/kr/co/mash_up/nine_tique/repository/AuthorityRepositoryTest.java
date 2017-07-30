package kr.co.mash_up.nine_tique.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.mash_up.nine_tique.NineTiqueApplication;
import kr.co.mash_up.nine_tique.domain.Authority;

import static org.junit.Assert.assertEquals;

/**
 * Created by ethankim on 2017. 7. 31..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NineTiqueApplication.class})
@DataJpaTest
@ActiveProfiles(profiles = "test")
public class AuthorityRepositoryTest {

    private static final String AUTHORITY_NAME = "name";

    @Autowired
    private AuthorityRepository authorityRepository;

    @Before
    public void setUp() throws Exception {
        Authority authority = new Authority();
        authority.setAuthority(AUTHORITY_NAME);
        authorityRepository.save(authority);
    }

    @Test
    public void findByAuthority_이름으로_권한_조회() throws Exception {
        // given : 이름으로
        String authorityName = AUTHORITY_NAME;

        // when : 권한을 조회하면
        Authority authority = authorityRepository.findByAuthority(authorityName);

        // then : 권한이 조회된다
        assertEquals(authority.getAuthority(), authorityName);
    }
}
