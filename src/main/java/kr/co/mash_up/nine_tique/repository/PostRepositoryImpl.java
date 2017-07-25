package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.Post;
import kr.co.mash_up.nine_tique.domain.QPost;

/**
 * Created by ethankim on 2017. 7. 25..
 */
public class PostRepositoryImpl implements PostRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QPost POST = QPost.post;

    @Override
    public Optional<Post> findOneByPostId(Long postId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(POST)
                .where(POST.id.eq(postId));

        return Optional.ofNullable(query.uniqueResult(POST));
    }

    @Override
    public Page<Post> findPosts(Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(POST)
                .orderBy(POST.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return new PageImpl<Post>(query.list(POST), pageable, query.count());
    }
}
