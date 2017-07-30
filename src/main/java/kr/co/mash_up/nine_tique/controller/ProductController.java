package kr.co.mash_up.nine_tique.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.ProductService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.vo.ProductListRequestVO;
import kr.co.mash_up.nine_tique.vo.ProductRequestVO;
import kr.co.mash_up.nine_tique.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_PRODUCT;

@RestController
@RequestMapping(value = API_PRODUCT)
@Slf4j
@Api(description = "상품", tags = {"product"})
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 상품 생성
     *
     * @param requestVO 생성할 상품 정보
     * @return 생성 결과
     */
    @ApiOperation(value = "상품 생성")
    @PostMapping
    public ResponseVO createProduct(@RequestBody ProductRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info(userId + " ");

        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getBrandName(), requestVO.getSize(),
                requestVO.getPrice(), requestVO.getDescription(), requestVO.getMainCategory(), requestVO.getImages());

        requestVO.setStatus(Product.Status.SELL.name());

        productService.addProduct(userId, requestVO);
        return ResponseVO.created();
    }

    /**
     * 상품 정보 수정 (상태변경 - 판매중/완료)
     * Todo: 상태변경 API patch로 분리
     *
     * @param productId 수정할 상품 id
     * @param requestVO 수정할 상품 정보
     * @return 수정 결과
     */
    @ApiOperation(value = "상품 정보 수정")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseVO updateProduct(@PathVariable("id") Long productId,
                             @RequestBody ProductRequestVO requestVO) {
        //Todo: status만 올때랑 name, brandName 등등이 올때를 구분해야 한다.. 좀더 개선 해보자
//        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getBrandName(), requestVO.getSize(),
//                requestVO.getPrice(), requestVO.getDescription(), requestVO.getStatus(), requestVO.getMainCategory(),
//                requestVO.getProductImages());

        Long userId = SecurityUtil.getCurrentUser().getId();

        if (requestVO.getStatus() != null) {
            productService.modifyProductStatus(userId, productId, requestVO.getStatus());
        } else {
            productService.modifyProduct(userId, productId, requestVO);
        }

        return ResponseVO.ok();
    }

    /**
     * 상품 정보 삭제
     *
     * @param productId 삭제할 상품 id
     * @return 삭제 결과
     */
    @ApiOperation(value = "상품 정보 삭제")
    @DeleteMapping(value = "/{product_id}")
    public ResponseVO deleteProduct(@PathVariable("product_id") Long productId) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        productService.removeProduct(userId, productId);
        return ResponseVO.ok();
    }

    /**
     * 상품 상세정보 조회
     *
     * @param productId 상세조회할 상품 id
     * @return 조회한 상품 정보
     */
    @ApiOperation(value = "상품 정보 조회")
    @GetMapping(value = "/{product_id}")
    public DataResponseVO<ProductDto> readProduct(@PathVariable("product_id") Long productId) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        ProductDto productDto = productService.readProduct(userId, productId);
        return new DataResponseVO<>(productDto);
    }

    /**
     * 카테고리별 상품 리스트 조회
     *
     * @param requestVO request parameter가 담긴 VO
     * @return 카테고리별 상품 리스트
     */
    @ApiOperation(value = "카테고리별 상품 리스트 조회")
    @GetMapping
    public DataListResponseVO<ProductDto> readProducts(ProductListRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getMainCategory());
        Long userId = SecurityUtil.getCurrentUser().getId();

        Page<ProductDto> page = productService.readProductsByCategory(userId, requestVO);

        log.debug(requestVO.getPageNo() + " " + requestVO.getPageSize() + " " + requestVO.getPageable() +
                " " + requestVO.getMainCategory() + " " + requestVO.getSubCategory());

        return new DataListResponseVO<>(page);
    }
}
