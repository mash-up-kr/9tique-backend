package kr.co.mash_up.nine_tique.repository.custom;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.QCategory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QCategory CATEGORY = QCategory.category;

    @Override
    public Optional<Category> findOneByMainAndSub(String main, String sub) {

        Category category =
                queryFactory.selectFrom(CATEGORY)
                        .where(CATEGORY.main.eq(main)
                                .and(CATEGORY.sub.eq(sub)))
                        .fetchOne();

        return Optional.ofNullable(category);
    }

    @Override
    public Optional<Category> findOneByCategoryId(Long categoryId) {

        Category category = queryFactory.selectFrom(CATEGORY)
                .where(CATEGORY.id.eq(categoryId))
                .fetchOne();

        return Optional.ofNullable(category);
    }
}
