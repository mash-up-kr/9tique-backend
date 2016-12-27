package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByMain(String main);

    Category findByMainAndSubAllIgnoreCase(String main, String sub);
}
