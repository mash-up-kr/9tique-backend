package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.vo.CategoryRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Category와 관련된 비즈니스 로직 처리
 */
@Service(value = "categorySservice")
@Slf4j
public class CategorySservice {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        Category category = categoryRepository.findOne(id);

        if (category == null) {
            throw new IdNotFoundException("category find by id -> category not found");
        }
        return category;
    }

    @Transactional
    public Category update(Long id, Category newCategory) {
        Category oldCateogry = categoryRepository.findOne(id);

        if (oldCateogry == null) {
            throw new IdNotFoundException("category update -> category not found");
        }

        oldCateogry.setMain(newCategory.getMain());
        oldCateogry.setSub(newCategory.getSub());
        return categoryRepository.save(oldCateogry);
    }

    @Transactional
    public Category create(CategoryRequestVO requestVO) {
        Category oldCategory = categoryRepository.findByMainAndSubAllIgnoreCase(requestVO.getMain(), requestVO.getSub());

        if (oldCategory != null) {  // 이미 존재할 경우
            throw new AlreadyExistException("category create -> category already exist");
        }
        return categoryRepository.save(requestVO.toCategoryEntity());
    }

    @Transactional
    public void delete(Long id) {
        Category oldCategory = categoryRepository.findOne(id);
        if (oldCategory == null) {
            throw new IdNotFoundException("category delete -> category not found");
        }
        categoryRepository.delete(id);
    }

}
