package kr.co.mash_up.nine_tique.repository.custom;

import java.util.List;
import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Category;

public interface CategoryRepositoryCustom {

    /**
     * 메인카테고리 이름과 하위 카테고리 이름으로 카테고리를 조회한다
     *
     * @param main
     * @param sub
     * @return
     */
    public abstract Optional<Category> findOneByMainAndSub(String main, String sub);

    /**
     * ID로 카테고리를 조회한다
     *
     * @param categoryId category ID
     * @return
     */
    public abstract Optional<Category> findOneByCategoryId(Long categoryId);

    /**
     * 카테고리 리스트를 조회한다
     *
     * @return
     */
    public abstract List<Category> findAll();
}
