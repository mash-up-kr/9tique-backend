package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import com.mysema.query.jpa.impl.JPAQuery;
import kr.co.mash_up.nine_tique.domain.PostComment;
import kr.co.mash_up.nine_tique.domain.QPost;
import kr.co.mash_up.nine_tique.domain.QPostComment;

/**
 * Created by ethankim on 2017. 7. 25..
 */
public class PostCommentRepositoryImpl implements PostCommentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QPost POST = QPost.post;

    private static final QPostComment POST_COMMENT = QPostComment.postComment;

    @Override
    public Optional<PostComment> findOneByPostIdAndCommentId(Long postId, Long commentId) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(POST_COMMENT)
                .join(POST_COMMENT.post, POST)
                .where(POST_COMMENT.id.eq(commentId)
                        .and(POST.id.eq(postId)));

        return Optional.ofNullable(query.uniqueResult(POST_COMMENT));
    }

    @Override
    public Page<PostComment> findPostComments(Long postId, Pageable pageable) {
        JPAQuery query = new JPAQuery(entityManager);

        query.from(POST_COMMENT)
                .where(POST_COMMENT.post.id.eq(postId))
                .orderBy(POST_COMMENT.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return new PageImpl<>(query.list(POST_COMMENT), pageable, query.count());
    }
}
