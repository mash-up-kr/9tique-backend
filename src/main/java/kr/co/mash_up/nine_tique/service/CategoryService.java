package kr.co.mash_up.nine_tique.service;

import java.util.List;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.web.vo.CategoryRequestVO;

/**
 * Category와 관련된 비즈니스 로직 처리를 담당한다
 * <p>
 * Created by ethankim on 2017. 7. 30..
 */
public interface CategoryService {

    /**
     * 카테고리 추가
     *
     * @param requestVO
     * @return
     */
    public abstract void addCategory(CategoryRequestVO requestVO);

    /**
     * 카테고리 수정
     *
     * @param categoryId
     * @param requestVO
     * @return
     */
    public abstract void modifyCategory(Long categoryId, CategoryRequestVO requestVO);

    /**
     * 카테고리 삭제
     *
     * @param categoryId
     */
    public abstract void removeCategory(Long categoryId);

    /**
     * 카테고리 리스트 조회
     *
     * @return
     */
    public abstract List<Category> readCategories();

    /**
     * 카테고리 ID로 카테고리 조회
     *
     * @param categoryId
     * @return
     */
    public abstract Category readCategory(Long categoryId);
}
