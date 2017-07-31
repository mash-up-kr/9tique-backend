package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이름으로 유저를 조회한다
     *
     * @param name user name
     * @return
     */
    @Deprecated
    public abstract User findByName(String name);

    /**
     * 이메일로 유저를 조회한다
     *
     * @param email user email
     * @return
     */
    public abstract Optional<User> findByEmail(String email);

    /**
     * 유저 ID로 유저를 조회한다
     *
     * @param userId 유저 ID
     * @return
     */
    public abstract Optional<User> findOneById(Long userId);
}
