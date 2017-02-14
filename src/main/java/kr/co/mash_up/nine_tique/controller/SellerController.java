package kr.co.mash_up.nine_tique.controller;


import kr.co.mash_up.nine_tique.domain.Seller;
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.dto.SellerDto;
import kr.co.mash_up.nine_tique.dto.UserDto;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.SellerService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_SELLER;

@RestController
@RequestMapping(value = API_SELLER)
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
    public DataListResponseVO<ProductDto> productList(DataListRequestVO requestVO) {
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

    /**
     * Todo: api명 수정 -> 현재 너무 이상함
     * seller의 shop & name 조회
     *
     * @return 조회한 정보
     */
    @GetMapping(value = "/info")
    public DataResponseVO<SellerDto> getSellerByUserId() {
        Long userId = SecurityUtil.getCurrentUser().getId();
        SellerDto sellerDto = sellerService.findSellerByUserId(userId);
        return new DataResponseVO<SellerDto>(sellerDto);
    }

    /**
     * 판매자 인증
     *
     * @param requestVO 인증코드 Wrapper
     * @return access token, 권한
     */
    @PutMapping(value = "/register")
    public DataResponseVO<UserDto> registerSeller(@RequestBody AuthentiCodeRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getAuthentiCode());
        Long userId = SecurityUtil.getCurrentUser().getId();

        UserDto userDto = sellerService.sellerAuthenticateAndAddAuthority(userId, requestVO.getAuthentiCode());
        return new DataResponseVO<UserDto>(userDto);
    }

    /**
     * 판매자 생성 -> 매장 연결, 인증코드 발급
     *
     * @param shopId 연결할 매장 id
     * @return 생성 결과
     */
    @PostMapping(value = "")
    public ResponseVO create(@RequestParam("shop_id") Long shopId) {
        ParameterUtil.checkParameterEmpty(shopId);

        Seller seller = sellerService.create(shopId);

        //Todo: 예외 상황 처리할 것
        if (seller == null) {
            return ResponseVO.ok();
        }
        return ResponseVO.ok();
    }

    /**
     * 판매자 정보 수정
     *
     * @param requestVO 수정될 판매자 정보
     * @return 수정 결과
     */
    @PutMapping(value = "")
    public ResponseVO update(@RequestBody SellerRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getSellerName(), requestVO.getShopName(), requestVO.getShopInfo(),
                requestVO.getShopPhone());
        Long userId = SecurityUtil.getCurrentUser().getId();

        Seller seller = sellerService.update(userId, requestVO);

        //Todo: 예외 상황 처리할 것
        if (seller == null) {
            return ResponseVO.ok();
        }
        return ResponseVO.ok();
    }

    /**
     * 판매자 리스트 정보 조회
     *
     * @param requestVO 페이지 정보
     * @return 판매자 리스트 정보
     */
    @GetMapping(value = "")
    public DataListResponseVO<SellerDto> list(DataListRequestVO requestVO) {
        Page<SellerDto> page = sellerService.findSellers(requestVO.getPageable());
        return new DataListResponseVO<SellerDto>(page);
    }
}