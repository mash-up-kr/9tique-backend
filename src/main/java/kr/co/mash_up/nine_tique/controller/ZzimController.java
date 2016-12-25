package kr.co.mash_up.nine_tique.controller;

import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.ZzimService;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/zzim")
@Slf4j
public class ZzimController {

    @Autowired
    private ZzimService zzimService;

    /**
     * 찜하기
     *
     * @param productId ZzimTable에 추가할 상품 id
     * @return 결과
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseVO add(@RequestParam(name = "product_id") Long productId) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        boolean result = zzimService.addProduct(userId, productId);

        if (result) {
            return ResponseVO.ok();  // 성공
        }
        return ResponseVO.ok();  // 실패
    }

    /**
     * 찜 해제
     *
     * @param productId 해제할 상품 id
     * @return 결과
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseVO delete(@PathVariable("id") Long productId) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        boolean result = zzimService.deleteProduct(userId, productId);
        if (result) {
            return ResponseVO.ok();  // 성공
        }
        return ResponseVO.ok();  // 실패
    }

    /**
     * 찜 목록 조회
     *
     * @param requestVO 페이지 정보
     * @return 찜 목록
     */
    @RequestMapping(method = RequestMethod.GET)
    public DataListResponseVO<ProductDto> list(DataListRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        Page<ProductDto> page = zzimService.findZzimProducts(userId, requestVO.getPageable());
        return new DataListResponseVO<ProductDto>(page);
    }
}