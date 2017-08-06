package kr.co.mash_up.nine_tique.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.mash_up.nine_tique.domain.Post;
import kr.co.mash_up.nine_tique.domain.PostProduct;
import kr.co.mash_up.nine_tique.domain.QPost;
import kr.co.mash_up.nine_tique.domain.QPostProduct;
import kr.co.mash_up.nine_tique.domain.QProduct;
import lombok.RequiredArgsConstructor;

/**
 * QueryDSL을 사용하여 Post Data를 처리한다
 * <p>
 * Created by ethankim on 2017. 7. 25..
 */
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QPost POST = QPost.post;

    private static final QPostProduct POST_PRODUCT = QPostProduct.postProduct;

    private static final QProduct PRODUCT = QProduct.product;

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

    // Todo: PRODUCT를 root로할 수 있게 수정
    @Override
    public Page<PostProduct> findPostProducts(Long postId, Pageable pageable) {

        QueryResults<PostProduct> results =
                queryFactory.selectFrom(POST_PRODUCT)
                        .join(POST_PRODUCT.post, POST)
                        .where(POST.id.eq(postId))
                        .orderBy(POST_PRODUCT.product.status.asc())
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        //        getQuerydsl().applyPagination(pageable, query);

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
