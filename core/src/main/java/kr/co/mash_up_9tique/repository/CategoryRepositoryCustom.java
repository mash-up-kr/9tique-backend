package kr.co.mash_up_9tique.repository;

import kr.co.mash_up_9tique.domain.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    Category findByMainAndSub(String main, String sub);

    Category findOneById(Long id);

    List<Category> findAll();
}