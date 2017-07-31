package kr.co.mash_up.nine_tique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.mash_up.nine_tique.domain.PostComment;
import kr.co.mash_up.nine_tique.repository.custom.PostCommentRepositoryCustom;

/**
 * Created by ethankim on 2017. 7. 25..
 */
@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long>, PostCommentRepositoryCustom {

}
