package kr.co.mash_up.nine_tique.controller;

import kr.co.mash_up.nine_tique.domain.*;
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.service.ProductService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 상품 생성
     *
     * @param requestVO 생성할 상품 정보
     * @return 생성 결과
     */
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseVO add(@RequestBody ProductRequestVO requestVO) {
//        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getBrandName(), requestVO.getSize(),
//                requestVO.getPrice(), requestVO.getDescription(), requestVO.getProductStatus(), requestVO.getSellerId(),
//                requestVO.getMainCategory(), requestVO.getFiles());
//
//        Product product = productService.save(requestVO);
//
//        if (product != null) {
//            return ResponseVO.ok();
//        }
//
//        //Todo: 생성 실패시 예외처리
//        return ResponseVO.ok();
//    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseVO add(@RequestParam(name = "name") String name,
                          @RequestParam(name = "brandName") String brandName,
                          @RequestParam(name = "size") String size,
                          @RequestParam(name = "price") int price,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "productStatus") String productStatus,
                          @RequestParam(name = "sellerId") long sellerId,
                          @RequestParam(name = "mainCategory") String mainCategory,
                          @RequestParam(name = "subCategory") String subCategory,
                          @RequestParam(name = "files") List<MultipartFile> files) {
        ParameterUtil.checkParameterEmpty(name, brandName, size, price, description, productStatus, sellerId,
                mainCategory, files);

        ProductRequestVO requestVO = new ProductRequestVO();
        requestVO.setName(name);
        requestVO.setBrandName(brandName);
        requestVO.setSize(size);
        requestVO.setPrice(price);
        requestVO.setDescription(description);
        requestVO.setSellerId(sellerId);
        requestVO.setMainCategory(mainCategory);
        requestVO.setSubCategory(subCategory);
        requestVO.setProductStatus(productStatus);
        requestVO.setFiles(files);

        Product product = productService.save(requestVO);

        if (product != null) {
            return ResponseVO.ok();
        }

        //Todo: 생성 실패시 예외처리
        return ResponseVO.ok();
    }

    /**
     * 상품 정보 수정 (상태변경 - 판매중/완료)
     * @param id 수정할 상품 id
     * @param requestVO 수정할 상품 정보
     * @return 수정 결과
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseVO update(@PathVariable Long id, @RequestBody ProductRequestVO requestVO) {
        // 바뀐 정보만 update하기 위해 service단에서 체크한다.
//        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getBrandName(), requestVO.getSize(),
//                requestVO.getPrice(), requestVO.getDescription(), requestVO.getProductStatus(), requestVO.getSellerId(),
//                requestVO.getMainCategory(), requestVO.getFiles());

        Product product = productService.update(id, requestVO);

        if (product != null) {
            return ResponseVO.ok();
        }

        //Todo: 업데이트 실패시 예외처리
        return ResponseVO.ok();
    }

    /**
     * 상품 상세정보 조회
     *
     * @param id 상세조회할 상품 id
     * @return 조회한 상품 정보
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DataResponseVO<ProductDto> detail(@PathVariable Long id) {
        ProductDto productDto = productService.findOne(id);
        return new DataResponseVO<ProductDto>(productDto);
    }

    /**
     * 카테고리별 리스트 조회
     *
     * @param requestVO request parameter가 담긴 VO
     * @return 카테고리별 상품 리스트
     */
    @RequestMapping(method = RequestMethod.GET)
    public DataListResponseVO<ProductDto> list(ProductListRequestVO requestVO) {

        Page<ProductDto> page = productService.findProductsByCategory(requestVO);

        log.debug(requestVO.getPageNo() + " " + requestVO.getPageSize() + " " + requestVO.getPageable() +
                " " + requestVO.getMainCategory() + " " + requestVO.getSubCategory());

        return new DataListResponseVO<ProductDto>(page);
    }

    /**
     * 상품 삭제
     *
     * @param id 삭제할 상품 id
     * @return 삭제 결과
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseVO delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseVO.ok();
    }

    /*
   찜 - 하기, 해제,
   유저의 찜테이블에 상품 id 추가
   유저정보, 상품 id
    */
    @RequestMapping(value = "/{id}/zzim", method = RequestMethod.PUT)
    public void zzim() {

    }
}
