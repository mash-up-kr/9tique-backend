package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.Zzim;
import kr.co.mash_up.nine_tique.repository.custom.ZzimRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZzimRepository extends JpaRepository<Zzim, Long>, ZzimRepositoryCustom {

}
