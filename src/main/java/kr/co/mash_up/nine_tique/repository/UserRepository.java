package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByEmail(String email);

    User findByOauthToken(String oauthToken);

    User findByAccessToken(String accessToken);

}
