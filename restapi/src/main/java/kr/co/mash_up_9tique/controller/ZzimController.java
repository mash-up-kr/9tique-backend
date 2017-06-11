package kr.co.mash_up_9tique.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.mash_up_9tique.dto.ProductDto;
import kr.co.mash_up_9tique.security.SecurityUtil;
import kr.co.mash_up_9tique.service.ZzimService;
import kr.co.mash_up_9tique.util.ParameterUtil;
import kr.co.mash_up_9tique.vo.DataListRequestVO;
import kr.co.mash_up_9tique.vo.DataListResponseVO;
import kr.co.mash_up_9tique.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static kr.co.mash_up_9tique.util.Constant.RestEndpoint.API_ZZIM;


@RestController
@RequestMapping(value = API_ZZIM)
@Slf4j
@Api(description = "찜", tags = {"zzim"})
public class ZzimController {

    @Autowired
    private ZzimService zzimService;

    /**
     * 찜 리스트에 상품 추가
     *
     * @param productId ZzimTable에 추가할 상품 id
     * @return 결과
     */
    @ApiOperation(value = "찜 상품 생성")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseVO add(@RequestParam(name = "product_id") Long productId) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        zzimService.addProduct(userId, productId);
        return ResponseVO.ok();
    }

    /**
     * 찜 리스트에서 상품 제거
     *
     * @param productId 해제할 상품 id
     * @return 결과
     */
    @ApiOperation(value = "찜 상품 제거")
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseVO delete(@PathVariable("id") Long productId) {
        ParameterUtil.checkParameterEmpty(productId);
        Long userId = SecurityUtil.getCurrentUser().getId();
        zzimService.deleteProduct(userId, productId);
        return ResponseVO.ok();
    }

    /**
     * 찜한 상품 리스트 조회
     *
     * @param requestVO 페이지 정보
     * @return 찜 목록
     */
    @ApiOperation(value = "찜한 상품 리스트 조회")
    @RequestMapping(method = RequestMethod.GET)
    public DataListResponseVO<ProductDto> list(DataListRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        Page<ProductDto> page = zzimService.findZzimProducts(userId, requestVO.getPageable());
        return new DataListResponseVO<ProductDto>(page);
    }
}