package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import kr.co.mash_up.nine_tique.dto.CommentDto;
import kr.co.mash_up.nine_tique.dto.PostDto;
import kr.co.mash_up.nine_tique.service.PostService;
import kr.co.mash_up.nine_tique.vo.CommentRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.PostRequestVO;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by ethankim on 2017. 7. 24..
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {


    @Override
    public void addPost(Long userId, PostRequestVO requestVO) {

    }

    @Override
    public void modifyPost(Long userId, Long postId, PostRequestVO requestVO) {

    }

    @Override
    public void removePost(Long userId, Long postId) {

    }

    @Override
    public Page<PostDto> readPosts(DataListRequestVO requestVO) {
        return null;
    }

    @Override
    public PostDto readPost(Long postId) {
        return null;
    }

    @Override
    public void addPostComment(Long postId, Long userId, CommentRequestVO requestVO) {

    }

    @Override
    public void modifyPostComment(Long postId, Long commentId, Long userId, CommentRequestVO requestVO) {

    }

    @Override
    public void removePostComment(Long postId, Long commentId, Long userId) {

    }

    @Override
    public Page<CommentDto> readPostComments(Long postId, DataListRequestVO requestVO) {
        return null;
    }
}
