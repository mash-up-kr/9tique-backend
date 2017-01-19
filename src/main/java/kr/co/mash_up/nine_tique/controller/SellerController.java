package kr.co.mash_up.nine_tique.controller;


import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.SellerService;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.vo.ProductDeleteRequestVO;
import kr.co.mash_up.nine_tique.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    /**
     * 판매자가 등록한 상품 리스트 조회
     *
     * @param requestVO request parameter가 담긴 VO
     * @return 판매자가 등록한 상품 리스트
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public DataListResponseVO<ProductDto> list(DataListRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();

        Page<ProductDto> page = sellerService.findProducts(userId, requestVO.getPageable());
        return new DataListResponseVO<ProductDto>(page);
    }

    /**
     * 판매자가 등록한 상품 전체삭제
     *
     * @return 삭제 결과
     */
//    @RequestMapping(value = "/products", method = RequestMethod.DELETE)
//    public ResponseVO deleteProductsAll() {
//        Long userId = SecurityUtil.getCurrentUser().getId();
//        sellerService.deleteProductsAll(userId);
//        return ResponseVO.ok();
//    }

    /**
     * 판매자가 등록한 상품 삭제
     *
     * @param requestVO 삭제할 상품 id가 담긴 VO
     * @return 삭제 결과
     */
    @RequestMapping(value = "/products", method = RequestMethod.DELETE)
    public ResponseVO deleteProducts(@RequestBody ProductDeleteRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        sellerService.deleteProducts(userId, requestVO);
        return ResponseVO.ok();
    }
}
