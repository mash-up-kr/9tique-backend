package kr.co.mash_up.nine_tique.repository;

import kr.co.mash_up.nine_tique.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByMain(String main);

    Category findByMainAndSubAllIgnoreCase(String main, String sub);
}
