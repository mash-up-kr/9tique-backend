package kr.co.mash_up.nine_tique.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.vo.CategoryRequestVO;
import lombok.extern.slf4j.Slf4j;

/**
 * Category와 관련된 비즈니스 로직 처리
 */
@Service(value = "categoryService")
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        Category category = categoryRepository.findOne(id);
        Optional.ofNullable(category).orElseThrow(() -> new IdNotFoundException("category find by id -> category not found"));

        return category;
    }

    @Transactional
    public Category update(Long id, Category newCategory) {
        Category oldCategory = categoryRepository.findOne(id);

        Optional.ofNullable(oldCategory).orElseThrow(() -> new IdNotFoundException("category update -> category not found"));

        oldCategory.update(newCategory);
        return categoryRepository.save(oldCategory);
    }

    @Transactional
    public Category create(CategoryRequestVO requestVO) {
        Category oldCategory = categoryRepository.findByMainAndSub(requestVO.getMain(), requestVO.getSub());

        // 이미 존재할 경우
        if (oldCategory != null && oldCategory.isActive()) {  // enable된걸 다시 등록하는 경우 -> error
            throw new AlreadyExistException("category create -> category already exist");
        } else if (oldCategory != null && !oldCategory.isActive()) {  // disable된걸 다시 등록하는 경우 enable
            oldCategory.enable();
            return categoryRepository.save(oldCategory);
        }

        // 아에 등록이 안된거면 새로 등록
//        savedCategory = Optional.ofNullable(oldCategory).orElseGet(() -> categoryRepository.save(requestVO.toCategoryEntity()));
        return categoryRepository.save(requestVO.toCategoryEntity());
    }

    /**
     * 카테고리 삭제
     * 실제로 삭제시키는게 아니라 disable시켜서 데이터는 보존. 유저들에겐 삭제된 것처럼 인식시킨다.
     *
     * @param categoryId 카테고리 id
     */
    @Transactional
    public void delete(Long categoryId) {
        Category oldCategory = categoryRepository.findOne(categoryId);
        Optional.ofNullable(oldCategory).orElseThrow(() -> new IdNotFoundException("category delete -> category not found"));

        oldCategory.disable();
        categoryRepository.save(oldCategory);
    }
}