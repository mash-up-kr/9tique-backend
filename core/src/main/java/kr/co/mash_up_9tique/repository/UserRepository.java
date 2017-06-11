package kr.co.mash_up_9tique.repository;

import kr.co.mash_up_9tique.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByEmail(String email);

    User findByOauthTokenAndOauthType(String oauthToken, User.OauthType oauthType);

//    User findByAccessToken(String accessToken);
}
