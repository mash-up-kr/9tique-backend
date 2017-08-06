package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.mash_up.nine_tique.domain.PostComment;
import kr.co.mash_up.nine_tique.domain.QPost;
import kr.co.mash_up.nine_tique.domain.QPostComment;
import lombok.RequiredArgsConstructor;

/**
 * QueryDSL을 사용하여 PostComment Data를 처리한다
 * <p>
 * Created by ethankim on 2017. 7. 25..
 */
@RequiredArgsConstructor
public class PostCommentRepositoryImpl implements PostCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QPost POST = QPost.post;

    private static final QPostComment POST_COMMENT = QPostComment.postComment;

    @Override
    public Optional<PostComment> findOneByPostIdAndCommentId(Long postId, Long commentId) {

        PostComment comment =
                queryFactory.selectFrom(POST_COMMENT)
                        .join(POST_COMMENT.post, POST)
                        .where(POST_COMMENT.id.eq(commentId)
                                .and(POST.id.eq(postId)))
                        .fetchOne();

        return Optional.ofNullable(comment);
    }

    @Override
    public Page<PostComment> findPostComments(Long postId, Pageable pageable) {

        QueryResults<PostComment> results =
                queryFactory.selectFrom(POST_COMMENT)
                        .where(POST_COMMENT.post.id.eq(postId))
                        .orderBy(POST_COMMENT.id.desc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
