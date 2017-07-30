package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.service.CategoryService;
import kr.co.mash_up.nine_tique.vo.CategoryRequestVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void addCategory(CategoryRequestVO requestVO) {
        Optional<Category> categoryOp = categoryRepository.findOneByMainAndSub(requestVO.getMain(), requestVO.getSub());
        categoryOp.ifPresent(category -> {
            throw new AlreadyExistException("addCategory -> category already exist");
        });

        categoryRepository.save(requestVO.toCategoryEntity());
    }

    @Transactional
    @Override
    public void modifyCategory(Long categoryId, CategoryRequestVO requestVO) {
        Optional<Category> categoryOp = categoryRepository.findOneByCategoryId(categoryId);
        Category category = categoryOp.orElseThrow(() -> new IdNotFoundException("modifyCategory -> category not found"));

        category.update(requestVO.toCategoryEntity());
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void removeCategory(Long categoryId) {
        Optional<Category> categoryOp = categoryRepository.findOneByCategoryId(categoryId);
        categoryOp.orElseThrow(() -> new IdNotFoundException("removeCategory -> category not found"));
        categoryRepository.delete(categoryId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> readCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Category readCategory(Long categoryId) {
        Optional<Category> categoryOp = categoryRepository.findOneByCategoryId(categoryId);
        Category category = categoryOp.orElseThrow(() -> new IdNotFoundException("readCategory -> category not found"));

        // Todo: category VO 생성?
        return category;
    }
}
