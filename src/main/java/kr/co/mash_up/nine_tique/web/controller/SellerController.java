package kr.co.mash_up.nine_tique.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.SellerService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.web.dto.ProductDto;
import kr.co.mash_up.nine_tique.web.dto.SellerDto;
import kr.co.mash_up.nine_tique.web.dto.UserDto;
import kr.co.mash_up.nine_tique.web.vo.AuthentiCodeRequestVO;
import kr.co.mash_up.nine_tique.web.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.web.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.web.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.web.vo.ProductDeleteRequestVO;
import kr.co.mash_up.nine_tique.web.vo.ResponseVO;
import kr.co.mash_up.nine_tique.web.vo.SellerRequestVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_SELLER;

/**
 * 판매자와 관련된 request를 처리한다
 * <p>
 * Created by ethankim on 2016. 10. 15..
 */
@RestController
@RequestMapping(value = API_SELLER)
@Slf4j
@Api(description = "판매자", tags = {"seller"})
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @ApiOperation(value = "판매자가 등록한 상품 리스트 조회", notes = "판매자가 등록한 상품 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공(없으면 빈 리스트)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping(value = "/products")
    public DataListResponseVO<ProductDto> readSellerProducts(DataListRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("readSellerProducts - userId : {}, page : {}", userId, requestVO);

        Page<ProductDto> page = sellerService.readSellerProducts(userId, requestVO.getPageable());
        return new DataListResponseVO<>(page);
    }

    @Deprecated
    @ApiOperation(value = "판매자가 등록한 상품 전체 삭제", notes = "판매자가 등록한 상품을 모두 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 판매자"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping(value = "/products/all")
    public ResponseVO removeProductsAll() {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("removeProductsAll - userId : {}", userId);

        sellerService.removeProductsAll(userId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "판매자가 등록한 상품 선택 삭제", notes = "판매자가 등록한 상품을 선택 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공"),
            @ApiResponse(code = 400, message = "판매자가 등록한 상품이 아님"),
            @ApiResponse(code = 404, message = "존재하지 않는 판매자 or 상품"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping(value = "/products")
    public ResponseVO removeProducts(@RequestBody ProductDeleteRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("removeProducts - userId : {}, products : {}", userId, requestVO);

        sellerService.removeProducts(userId, requestVO);
        return ResponseVO.ok();
    }

    /**
     * Todo: api명 수정 -> 현재 너무 이상함
     */
    @ApiOperation(value = "판매자 정보(이름, 매장 정보등) 조회", notes = "판매자의 정보(이름, 매장 정보)를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 판매자"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping(value = "/info")
    public DataResponseVO<SellerDto> readSellerInfo() {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("readSellerInfo - userId : {}", userId);

        SellerDto sellerDto = sellerService.readSellerInfo(userId);
        return new DataResponseVO<>(sellerDto);
    }

    @ApiOperation(value = "판매자 인증", notes = "인증코드로 판매자인지 인증한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "인증 성공"),
            @ApiResponse(code = 400, message = "이미 판매자로 등록된 인증코드 or 유저"),
            @ApiResponse(code = 404, message = "존재하지 않는 유저"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PutMapping(value = "/register")
    public DataResponseVO<UserDto> registerSeller(@RequestBody AuthentiCodeRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("registerSeller - userId : {}, authentiCode : {}", userId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getAuthentiCode());
        UserDto userDto = sellerService.registerSeller(userId, requestVO.getAuthentiCode());
        return new DataResponseVO<>(userDto);
    }

    // Todo: shop_id가 없을 경우 여기로 request가 오는지 확인. 안오면 parameter check 제거, 오면 유지
    @ApiOperation(value = "판매자 정보 생성", notes = "매장의 판매자를 생성하고 인증코드 발급한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "생성 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 매장"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping
    public ResponseVO addSeller(@RequestParam("shop_id") Long shopId) {
        log.info("addSeller - shopId : {}", shopId);

        ParameterUtil.checkParameterEmpty(shopId);
        sellerService.addSeller(shopId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "판매자 정보 수정", notes = "판매자의 정보를 수정한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 판매자"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PutMapping
    public ResponseVO modifySeller(@RequestBody SellerRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("modifySeller - userId : {}, seller : {}", userId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getSellerName(), requestVO.getShopName(), requestVO.getShopInfo(),
                requestVO.getShopPhone());
        sellerService.modifySeller(userId, requestVO);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "판매자 리스트 조회", notes = "판매자 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping
    public DataListResponseVO<SellerDto> readSellers(DataListRequestVO requestVO) {
        log.info("readSellers - page : {}", requestVO);

        Page<SellerDto> page = sellerService.readSellers(requestVO.getPageable());
        return new DataListResponseVO<>(page);
    }
}