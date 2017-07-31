package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.mash_up.nine_tique.domain.Authority;

/**
 * Authority 엔티티의 CRUD처리 담당
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    /**
     * 이름으로 권한을 조회한다
     *
     * @param name
     * @return
     */
    public abstract Authority findByAuthority(String name);
}
