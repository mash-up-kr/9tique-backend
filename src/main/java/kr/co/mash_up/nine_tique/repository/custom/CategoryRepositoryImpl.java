package kr.co.mash_up.nine_tique.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.QCategory;


public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QCategory CATEGORY = QCategory.category;

    @Override
    public Optional<Category> findOneByMainAndSub(String main, String sub) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(CATEGORY)
//                .where(qCategory.main.eq(main).and(qCategory.sub.eq(sub)).and(qCategory.enable.isTrue()));
                .where(CATEGORY.main.eq(main)
                        .and(CATEGORY.sub.eq(sub)));

        return Optional.ofNullable(query.uniqueResult(CATEGORY));
    }

    @Override
    public Optional<Category> findOneByCategoryId(Long categoryId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(CATEGORY)
//                .where(qCategory.id.eq(categoryId).and(qCategory.active.isTrue()));
                .where(CATEGORY.id.eq(categoryId));

        return Optional.ofNullable(query.uniqueResult(CATEGORY));
    }

    @Override
    public List<Category> findAll() {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(CATEGORY)
                .orderBy(CATEGORY.id.desc());

        return query.list(CATEGORY);
    }
}
