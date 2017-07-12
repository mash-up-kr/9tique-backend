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
import kr.co.mash_up.nine_tique.dto.PromotionDto;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.PromotionService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.vo.PromotionRequestVO;
import kr.co.mash_up.nine_tique.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_PROMOTION;

/**
 * 프로모션과 관련된 request를 처리한다
 * <p>
 * Created by ethankim on 2017. 7. 9..
 */
@RestController
@RequestMapping(value = API_PROMOTION)
@Slf4j
@Api(description = "프로모션", tags = {"promotion"})
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @ApiOperation(value = "프로모션 추가", notes = "프로모션을 추가한다")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "추가 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping
    public ResponseVO addPromotion(@RequestBody PromotionRequestVO promotionVO) {
        log.info("addPromotion - promotion : {}", promotionVO);

        Long userId = SecurityUtil.getCurrentUser().getId();
        ParameterUtil.checkParameterEmpty(promotionVO.getName(), promotionVO.getDescription());
        promotionService.addPromotion(userId, promotionVO);
        return ResponseVO.created();
    }

    @ApiOperation(value = "프로모션 수정", notes = "프로모션을 수정한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청(필수 파라미터 누락)"),
            @ApiResponse(code = 404, message = "존재하지 않는 프로모션"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PutMapping(value = "/{promotion_id}")
    public ResponseVO modifyPromotion(@PathVariable(value = "promotion_id") Long promotionId,
                                      @RequestBody PromotionRequestVO promotionVO) {
        log.info("modifyPromotion - promotionId : {}, promotion : {}", promotionId, promotionVO);

        ParameterUtil.checkParameterEmpty(promotionVO.getName(), promotionVO.getDescription());
        promotionService.modifyPromotion(promotionId, promotionVO);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "프로모션 삭제", notes = "프로모션을 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 프로모션"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping(value = "/{promotion_id}")
    public ResponseVO removePromotion(@PathVariable(value = "promotion_id") Long promotionId) {
        log.info("removePromotion - promotionId : {}", promotionId);

        promotionService.removePromotion(promotionId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "프로모션 리스트 조회", notes = "프로모션 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping
    public DataListResponseVO<PromotionDto> readPromotions(DataListRequestVO requestVO) {
        log.info("readPromotions - page : {}", requestVO);

        Page<PromotionDto> promotions = promotionService.readPromotions(requestVO);
        return new DataListResponseVO<>(promotions);
    }

    @ApiOperation(value = "프로모션 상세정보 조회", notes = "프로모션 상세정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 프로모션"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping(value = "/{promotion_id}")
    public DataResponseVO<PromotionDto> readPromotion(@PathVariable(value = "promotion_id") Long promotionId) {
        log.info("readPromotion - promotionId : {}", promotionId);

        PromotionDto promotion = promotionService.readPromotion(promotionId);
        return new DataResponseVO<>(promotion);
    }
}
