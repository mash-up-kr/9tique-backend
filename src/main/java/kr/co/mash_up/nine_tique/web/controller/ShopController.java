package kr.co.mash_up.nine_tique.web.controller;

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
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.impl.ShopServiceImpl;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.web.dto.CommentDto;
import kr.co.mash_up.nine_tique.web.dto.ShopDto;
import kr.co.mash_up.nine_tique.web.vo.CommentRequestVO;
import kr.co.mash_up.nine_tique.web.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.web.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.web.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.web.vo.ResponseVO;
import kr.co.mash_up.nine_tique.web.vo.ShopRequestVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_SHOP;

/**
 * 매장에 관련된 request를 처리한다
 * <p>
 * Created by ethankim on 2016. 10. 8..
 */
@RestController
@RequestMapping(value = API_SHOP)
@Slf4j
@Api(description = "매장", tags = {"shop"})
public class ShopController {

    @Autowired
    private ShopServiceImpl shopService;

    @ApiOperation(value = "매장 추가", notes = "매장 정보를 추가한다")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "추가 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락, 이미 존재하는 매장)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping
    public ResponseVO addShop(@RequestBody ShopRequestVO requestVO) {
        log.info("addShop - shop : {}", requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getDescription(), requestVO.getPhoneNumber());
        shopService.addShop(requestVO);
        return ResponseVO.created();
    }

    @ApiOperation(value = "매장 수정", notes = "매장 정보를 수정한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 404, message = "존재하지 않는 매장"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PutMapping(value = "/{shop_id}")
    public ResponseVO modifyShop(@PathVariable("shop_id") Long shopId, @RequestBody ShopRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("modifyShop - userId : {}, shopId : {}, shop : {}", userId, shopId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getDescription(), requestVO.getPhoneNumber());
        shopService.modifyShop(userId, shopId, requestVO);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "매장 삭제", notes = "매장 정보를 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 매장"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping(value = "/{shop_id}")
    public ResponseVO removeShop(@PathVariable("shop_id") Long shopId) {
        log.info("removeShop - shopId : {}", shopId);

        shopService.removeShop(shopId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "매장 리스트 조회", notes = "매장 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping
    public DataListResponseVO<ShopDto> readShops(DataListRequestVO requestVO) {
        log.info("readShops - page : {}", requestVO);

        Page<ShopDto> page = shopService.readShops(requestVO);
        return new DataListResponseVO<>(page);
    }

    @ApiOperation(value = "매장 상세정보 조회", notes = "매장 상세정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 매장"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping(value = "/{shop_id}")
    public DataResponseVO<ShopDto> readShop(@PathVariable("shop_id") Long shopId) {
        log.info("readShop - shopId : {}", shopId);

        ShopDto shopDto = shopService.readShop(shopId);
        return new DataResponseVO<>(shopDto);
    }

    @ApiOperation(value = "매장 댓글 추가", notes = "매장의 댓글을 추가한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "추가 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 404, message = "존재하지 않는 매장"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping("/{shop_id}/comments")
    public ResponseVO addShopComment(@PathVariable(value = "shop_id") Long shopId, @RequestBody CommentRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("addShopComment - userId : {}, shopId : {}, comment : {}", userId, shopId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getContents());
        shopService.addShopComment(shopId, userId, requestVO);
        return ResponseVO.created();
    }

    @ApiOperation(value = "매장 댓글 수정", notes = "매장의 댓글을 수정한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락, 작성자 불일치)"),
            @ApiResponse(code = 404, message = "존재하지 않는 매장"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PutMapping("/{shop_id}/comments/{comment_id}")
    public ResponseVO modifyShopComment(@PathVariable(value = "shop_id") Long shopId, @PathVariable(value = "comment_id") Long commentId,
                                        @RequestBody CommentRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("modifyShopComment - userId : {}, shopId : {}, commentId : {}, comment: {}", userId, shopId, commentId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getContents());
        shopService.modifyShopComment(shopId, commentId, userId, requestVO);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "매장 댓글 삭제", notes = "매장의 댓글을 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(작성자 불일치)"),
            @ApiResponse(code = 404, message = "존재하지 않는 댓글"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping("/{shop_id}/comments/{comment_id}")
    public ResponseVO removeShopComment(@PathVariable(value = "shop_id") Long shopId,
                                        @PathVariable(value = "comment_id") Long commentId) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("removeShopComment - userId : {}, shopId : {}, commentId : {}", userId, shopId, commentId);

        shopService.removeShopComment(shopId, commentId, userId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "매장 댓글 리스트 조회", notes = "매장의 댓글 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping("/{shop_id}/comments")
    public DataListResponseVO<CommentDto> readShopComments(@PathVariable(value = "shop_id") Long shopId,
                                                           DataListRequestVO requestVO) {
        log.info("addShopComment - shopId : {}, page : {}", shopId, requestVO);

        Page<CommentDto> page = shopService.readShopComments(shopId, requestVO);
        return new DataListResponseVO<>(page);
    }
}
