package kr.co.mash_up.nine_tique.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.mash_up.nine_tique.dto.CommentDto;
import kr.co.mash_up.nine_tique.dto.PostDto;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.PostService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.CommentRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.vo.PostRequestVO;
import kr.co.mash_up.nine_tique.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_POST;

/**
 * 게시물과 관련된 request를 처리한다
 * <p>
 * Created by ethankim on 2017. 7. 22..
 */
@RestController
@RequestMapping(value = API_POST)
@Slf4j
@Api(description = "게시물", tags = {"post"})
public class PostController {

    @Autowired
    private PostService postService;

    @ApiOperation(value = "게시물 추가", notes = "게시물을 추가한다")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "추가 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping
    public ResponseVO addPost(@RequestBody PostRequestVO postVO) {
        log.info("addPost - post : {}", "");

        Long userId = SecurityUtil.getCurrentUser().getId();
        // Todo: parameter validation
        postService.addPost(userId, postVO);
        return ResponseVO.created();
    }

    @ApiOperation(value = "게시물 수정", notes = "게시물을 수정한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 404, message = "존재하지 않는 게시물"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PutMapping(value = "/{post_id}")
    public ResponseVO modifyPost(@PathVariable(value = "post_id") Long postId,
                                 @RequestBody PostRequestVO postVO) {
        log.info("modifyPost - postId : {}, post : {}", postId, postVO);

        Long userId = SecurityUtil.getCurrentUser().getId();
        // Todo: parameter validation
        postService.modifyPost(userId, postId, postVO);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "게시물 삭제", notes = "게시물을 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 게시물"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping(value = "/{post_id}")
    public ResponseVO removePost(@PathVariable(value = "post_id") Long postId) {
        log.info("removePost - post_id : {}", postId);

        Long userId = SecurityUtil.getCurrentUser().getId();
        postService.removePost(userId, postId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "게시물 리스트 조회", notes = "게시물 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping
    public DataListResponseVO<PostDto> readPosts(DataListRequestVO requestVO) {
        log.info("readPosts - page : {}", requestVO);

        Page<PostDto> page = postService.readPosts(requestVO);
        return new DataListResponseVO<>(page);
    }

    @ApiOperation(value = "게시물 상세정보 조회", notes = "게시물 상세정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 프로모션"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping(value = "/{post_id}")
    public DataResponseVO<PostDto> readPost(@PathVariable(value = "post_id") Long postId) {
        log.info("readPost - postId : {}", postId);

        PostDto post = postService.readPost(postId);
        return new DataResponseVO<>(post);
    }

    @ApiOperation(value = "게시물 댓글 추가", notes = "게시물의 댓글을 추가한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "추가 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 404, message = "존재하지 않는 게시물"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping("/{post_id}/comments")
    public ResponseVO addPostComment(@PathVariable(value = "post_id") Long postId,
                                     @RequestBody CommentRequestVO requestVO) {
        log.info("addPostComment - postId : {}, comment : {}", postId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getContents());
        Long userId = SecurityUtil.getCurrentUser().getId();
        postService.addPostComment(postId, userId, requestVO);
        return ResponseVO.created();
    }

    @ApiOperation(value = "게시물 댓글 수정", notes = "게시물의 댓글을 수정한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락, 작성자 불일치)"),
            @ApiResponse(code = 404, message = "존재하지 않는 게시물"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PutMapping("/{post_id}/comments/{comment_id}")
    public ResponseVO modifyPostComment(@PathVariable(value = "post_id") Long postId,
                                        @PathVariable(value = "comment_id") Long commentId,
                                        @RequestBody CommentRequestVO requestVO) {
        log.info("modifyPostComment - postId : {}, commentId : {}, comment: {}", postId, commentId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getContents());
        Long userId = SecurityUtil.getCurrentUser().getId();
        postService.modifyPostComment(postId, commentId, userId, requestVO);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "게시물 댓글 삭제", notes = "게시물의 댓글을 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(작성자 불일치)"),
            @ApiResponse(code = 404, message = "존재하지 않는 게시물"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping("/{post_id}/comments/{comment_id}")
    public ResponseVO removePostComment(@PathVariable(value = "post_id") Long postId,
                                        @PathVariable(value = "comment_id") Long commentId) {
        log.info("removePostComment - postId : {}, commentId : {}", postId, commentId);

        Long userId = SecurityUtil.getCurrentUser().getId();
        postService.removePostComment(postId, commentId, userId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "게시물 댓글 리스트 조회", notes = "게시물의 댓글 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping("/{post_id}/comments")
    public DataListResponseVO<CommentDto> readPostComments(@PathVariable(value = "post_id") Long postId,
                                                           @RequestBody DataListRequestVO requestVO) {
        log.info("readPostComments - postId : {}, page : {}", postId, requestVO);

        Page<CommentDto> page = postService.readPostComments(postId, requestVO);
        return new DataListResponseVO<>(page);
    }
}
