package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategorySservice {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findOne(Long id) {
        return categoryRepository.findOne(id);
    }

    //Todo: update logic implement
    @Transactional
    public Category update(Long id, Category newCategory) {
        Category oldCateogry = categoryRepository.findOne(id);
        if(oldCateogry != null){
            oldCateogry.setMain(newCategory.getMain());
            oldCateogry.setSub(newCategory.getSub());
        }

        return categoryRepository.save(oldCateogry);
    }

    @Transactional
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.delete(id);
    }

}
