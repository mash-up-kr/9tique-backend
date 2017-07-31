package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.repository.custom.CategoryRepositoryCustom;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    /**
     * 메인 카테고리로 카테고리 리스트를 조회한다
     *
     * @param main
     * @return
     */
    @Deprecated
    public abstract List<Category> findByMain(String main);
}
