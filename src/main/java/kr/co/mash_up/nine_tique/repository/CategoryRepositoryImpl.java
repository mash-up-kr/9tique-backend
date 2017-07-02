package kr.co.mash_up.nine_tique.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.QCategory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Category findByMainAndSub(String main, String sub) {
        JPAQuery query = new JPAQuery(entityManager);
        QCategory qCategory = QCategory.category;

        query.from(qCategory)
//                .where(qCategory.main.eq(main).and(qCategory.sub.eq(sub)).and(qCategory.enable.isTrue()));
                .where(qCategory.main.eq(main).and(qCategory.sub.eq(sub)));

        return query.uniqueResult(qCategory);
    }

    @Override
    public Category findOneById(Long id) {
        JPAQuery query = new JPAQuery(entityManager);
        QCategory qCategory = QCategory.category;

        query.from(qCategory)
                .where(qCategory.id.eq(id).and(qCategory.active.isTrue()));

        return query.uniqueResult(qCategory);
    }

    @Override
    public List<Category> findAll() {
        JPAQuery query = new JPAQuery(entityManager);
        QCategory qCategory = QCategory.category;

        query.from(qCategory)
                .where(qCategory.active.isTrue())
                .orderBy(qCategory.createdAt.desc());

        return query.list(qCategory);
    }
}