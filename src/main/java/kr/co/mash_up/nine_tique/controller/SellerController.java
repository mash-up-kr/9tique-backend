package kr.co.mash_up.nine_tique.controller;


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
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.dto.SellerDto;
import kr.co.mash_up.nine_tique.dto.UserDto;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.SellerService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.AuthentiCodeRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.vo.ProductDeleteRequestVO;
import kr.co.mash_up.nine_tique.vo.ResponseVO;
import kr.co.mash_up.nine_tique.vo.SellerRequestVO;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_SELLER;

@RestController
@RequestMapping(value = API_SELLER)
@Api(description = "판매자", tags = {"seller"})
public class SellerController {

    @Autowired
    private SellerService sellerService;

    /**
     * 판매자가 등록한 상품 리스트 조회
     *
     * @param requestVO request parameter가 담긴 VO
     * @return 판매자가 등록한 상품 리스트
     */
    @ApiOperation(value = "판매자가 등록한 상품 리스트 조회")
    @GetMapping(value = "/products")
    public DataListResponseVO<ProductDto> productList(DataListRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        Page<ProductDto> page = sellerService.readProducts(userId, requestVO.getPageable());
        return new DataListResponseVO<>(page);
    }

    /**
     * 판매자가 등록한 상품 전체삭제
     *
     * @return 삭제 결과
     */
    @Deprecated
    @DeleteMapping(value = "/products")
    public ResponseVO deleteProductsAll() {
        Long userId = SecurityUtil.getCurrentUser().getId();
        sellerService.removeProductsAll(userId);
        return ResponseVO.ok();
    }

    /**
     * 판매자가 등록한 상품 선택 삭제
     *
     * @param requestVO 삭제할 상품 id가 담긴 VO
     * @return 삭제 결과
     */
    @ApiOperation(value = "판매자가 등록한 상품 선택 삭제")
    @DeleteMapping(value = "/products")
    public ResponseVO deleteProducts(@RequestBody ProductDeleteRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        sellerService.removeProducts(userId, requestVO);
        return ResponseVO.ok();
    }

    /**
     * Todo: api명 수정 -> 현재 너무 이상함
     * 판매자의 이름, Shop 정보 조회
     *
     * @return 조회한 정보
     */
    @ApiOperation(value = "판매자의 이름, 매장 정보 조회")
    @GetMapping(value = "/info")
    public DataResponseVO<SellerDto> getSellerByUserId() {
        Long userId = SecurityUtil.getCurrentUser().getId();
        SellerDto sellerDto = sellerService.findSellerInfo(userId);
        return new DataResponseVO<>(sellerDto);
    }

    /**
     * 판매자 인증
     *
     * @param requestVO 인증코드 Wrapper
     * @return access token, 권한
     */
    @ApiOperation(value = "판매자 인증")
    @PutMapping(value = "/register")
    public DataResponseVO<UserDto> registerSeller(@RequestBody AuthentiCodeRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getAuthentiCode());
        Long userId = SecurityUtil.getCurrentUser().getId();

        UserDto userDto = sellerService.registerSeller(userId, requestVO.getAuthentiCode());
        return new DataResponseVO<>(userDto);
    }

    /**
     * 판매자 정보 생성 -> 기존에 등록된 매장에 연결 및 인증코드 발급
     *
     * @param shopId 연결할 매장 id
     * @return 생성 결과
     */
    @ApiOperation(value = "판매자 정보 생성")
    @PostMapping
    // Todo: 여기로 request가 오는지 확인
    public ResponseVO create(@RequestParam("shop_id") Long shopId) {
        ParameterUtil.checkParameterEmpty(shopId);
        sellerService.addSeller(shopId);
        return ResponseVO.ok();
    }

    /**
     * 판매자 정보 수정
     *
     * @param requestVO 수정될 판매자 정보
     * @return 수정 결과
     */
    @ApiOperation(value = "판매자 정보 수정")
    @PutMapping
    public ResponseVO update(@RequestBody SellerRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getSellerName(), requestVO.getShopName(), requestVO.getShopInfo(),
                requestVO.getShopPhone());
        Long userId = SecurityUtil.getCurrentUser().getId();
        sellerService.modifySeller(userId, requestVO);
        return ResponseVO.ok();
    }

    /**
     * 판매자 리스트 조회
     *
     * @param requestVO 페이지 정보
     * @return 판매자 리스트 정보
     */
    @ApiOperation(value = "판매자 리스트 조회")
    @GetMapping
    public DataListResponseVO<SellerDto> list(DataListRequestVO requestVO) {
        Page<SellerDto> page = sellerService.readSellers(requestVO.getPageable());
        return new DataListResponseVO<>(page);
    }
}