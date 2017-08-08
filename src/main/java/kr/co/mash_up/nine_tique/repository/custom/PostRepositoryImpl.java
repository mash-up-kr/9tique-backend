package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.Optional;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.mash_up.nine_tique.domain.Post;
import kr.co.mash_up.nine_tique.domain.QPost;

/**
 * QueryDSL을 사용하여 Post Data를 처리한다
 * <p>
 * Created by ethankim on 2017. 7. 25..
 */
public class PostRepositoryImpl extends QueryDslRepositorySupport implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QPost POST = QPost.post;

    public PostRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<Post> findOneByPostId(Long postId) {

        Post post = queryFactory.selectFrom(POST)
                .where(POST.id.eq(postId))
                .fetchOne();

        return Optional.ofNullable(post);
    }

    @Override
    public Page<Post> findPosts(Pageable pageable) {

        QueryResults<Post> results = queryFactory.selectFrom(POST)
                .orderBy(POST.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
