package kr.co.mash_up.nine_tique.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.mash_up.nine_tique.web.dto.ProductDto;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.ZzimService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.web.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.web.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.web.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_ZZIM;

@RestController
@RequestMapping(value = API_ZZIM)
@Slf4j
@Api(description = "찜", tags = {"zzim"})
public class ZzimController {

    @Autowired
    private ZzimService zzimService;

    @ApiOperation(value = "찜 상품 추가", notes = "유저의 찜목록에 상품을 추가한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "찜 추가 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping
    public ResponseVO createZzimProduct(@RequestParam(name = "product_id") Long productId) {
        log.info("createZzimProduct - productId : {}", productId);

        Long userId = SecurityUtil.getCurrentUser().getId();
        zzimService.addZzimProduct(userId, productId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "찜 상품 제거", notes = "유저의 찜목록에서 상품을 제거한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "찜 취소 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping(value = "/product/{product_id}")
    public ResponseVO deleteZzimProduct(@PathVariable("product_id") Long productId) {
        log.info("deleteZzimProduct - productId : {}", productId);

        ParameterUtil.checkParameterEmpty(productId);
        Long userId = SecurityUtil.getCurrentUser().getId();
        zzimService.removeZzimProduct(userId, productId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "찜한 상품 리스트 조회", notes = "유저의 찜한 상품 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "찜한 상품 리스트 조회"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping
    public DataListResponseVO<ProductDto> getZzimProducts(DataListRequestVO requestVO) {
        log.info("getZzimProducts {}", requestVO);

        Long userId = SecurityUtil.getCurrentUser().getId();
        Page<ProductDto> page = zzimService.readZzimProducts(userId, requestVO.getPageable());
        return new DataListResponseVO<>(page);
    }
}
