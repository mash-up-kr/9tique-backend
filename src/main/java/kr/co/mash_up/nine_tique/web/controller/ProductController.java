package kr.co.mash_up.nine_tique.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import kr.co.mash_up.nine_tique.service.ProductService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.web.dto.ProductDto;
import kr.co.mash_up.nine_tique.web.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.web.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.web.vo.ProductListRequestVO;
import kr.co.mash_up.nine_tique.web.vo.ProductRequestVO;
import kr.co.mash_up.nine_tique.web.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_PRODUCT;

/**
 * 상품과 관련된 request를 처리한다
 * <p>
 * Created by ethankim on 2016. 10. 22..
 */
@RestController
@RequestMapping(value = API_PRODUCT)
@Slf4j
@Api(description = "상품", tags = {"product"})
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "상품 추가", notes = "상품 정보를 추가한다")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "상품 추가 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 판매자 or 카테고리 or 이미지"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping
    public ResponseVO addProduct(@RequestBody ProductRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("addProduct - userId : {}, product : {}", userId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getBrandNameEng(), requestVO.getSize(),
                requestVO.getPrice(), requestVO.getDescription(), requestVO.getMainCategory(), requestVO.getImages());
        productService.addProduct(userId, requestVO);
        return ResponseVO.created();
    }

    @ApiOperation(value = "상품 정보 수정", notes = "상품 정보를 수정한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "상품 수정 성공"),
            @ApiResponse(code = 400, message = "등록자가 아님"),
            @ApiResponse(code = 404, message = "존재하지 않는 상품 or 판매자 or 카테고리 or 이미지"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PutMapping(value = "/{product_id}")
    public ResponseVO modifyProduct(@PathVariable(value = "product_id") Long productId, @RequestBody ProductRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("modifyProduct - userId : {}, productId : {}, product : {}", userId, productId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getBrandNameEng(), requestVO.getSize(),
                requestVO.getPrice(), requestVO.getDescription(), requestVO.getMainCategory(), requestVO.getImages());
        productService.modifyProduct(userId, productId, requestVO);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "상품 상태 수정", notes = "상품의 상태(판매중/완료)를 수정한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "상품 상태 수정 성공"),
            @ApiResponse(code = 400, message = "등록자가 아님 or 이전과 같은 상태"),
            @ApiResponse(code = 404, message = "존재하지 않는 상품 or 판매자"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PatchMapping(value = "/{product_id}")
    public ResponseVO modifyProductStatus(@PathVariable(value = "product_id") Long productId, @RequestBody ProductRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("modifyProductStatus - userId : {}, productId : {}, product : {}", userId, productId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getStatus());
        productService.modifyProductStatus(userId, productId, requestVO.getStatus());
        return ResponseVO.ok();
    }

    @ApiOperation(value = "상품 정보 삭제", notes = "상품 정보를 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "상품 삭제 성공"),
            @ApiResponse(code = 400, message = "등록자가 아님"),
            @ApiResponse(code = 404, message = "존재하지 않는 상품 or 판매자"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping(value = "/{product_id}")
    public ResponseVO removeProduct(@PathVariable(value = "product_id") Long productId) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("removeProduct - userId : {}, productId : {}", userId, productId);

        productService.removeProduct(userId, productId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "상품 상세정보 조회", notes = "상품의 상세정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 상품"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping(value = "/{product_id}")
    public DataResponseVO<ProductDto> readProduct(@PathVariable(value = "product_id") Long productId) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("readProduct - userId : {}, productId : {}", userId, productId);

        ProductDto productDto = productService.readProduct(userId, productId);
        return new DataResponseVO<>(productDto);
    }
    
    @ApiOperation(value = "카테고리별 상품 리스트 조회", notes = "카테고리별 상품 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "상품 삭제 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 카테고리"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping
    public DataListResponseVO<ProductDto> readProducts(ProductListRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("readProducts - userId : {}, page : {}", userId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getMainCategory());
        Page<ProductDto> page = productService.readProductsByCategory(userId, requestVO);
        return new DataListResponseVO<>(page);
    }
}
