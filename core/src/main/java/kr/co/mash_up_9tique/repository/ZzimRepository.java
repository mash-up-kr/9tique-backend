package kr.co.mash_up_9tique.repository;

import kr.co.mash_up_9tique.domain.Zzim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZzimRepository extends JpaRepository<Zzim, Long>, ZzimRepositoryCustom {
}
