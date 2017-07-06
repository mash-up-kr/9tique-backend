package kr.co.mash_up.nine_tique.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.mash_up.nine_tique.service.BrandService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.BrandVO;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_BRAND;

/**
 * 브랜드에 관련된 request를 처리한다
 * <p>
 * Created by ethankim on 2017. 7. 6..
 */
@RestController
@RequestMapping(value = API_BRAND)
@Slf4j
@Api(description = "브랜드", tags = {"brand"})
public class BrandController {

    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "브랜드 생성", notes = "브랜드를 생성한다")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "생성 성공"),
            @ApiResponse(code = 400, message = "잘못된 파라미터(필수 파라미터 누락, 존재하는 브랜드)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping
    private ResponseVO addBrand(@RequestBody BrandVO brandVO) {
        log.debug("addBrand nameKo : {}, nameEng : {}", brandVO.getNameKo(), brandVO.getNameEng());

        ParameterUtil.checkParameterEmpty(brandVO.getNameKo(), brandVO.getNameEng());
        brandService.addBrand(brandVO);
        return ResponseVO.created();
    }

    @ApiOperation(value = "브랜드 수정", notes = "브랜드를 수정한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 브랜드"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PutMapping(value = "/{brand_id}")
    private ResponseVO modifyBrand(@PathVariable(value = "brand_id") Long brandId,
                                   @RequestBody BrandVO brandVO) {
        log.debug("modifyBrand id : {}, nameKo : {}, nameEng : {}", brandId, brandVO.getNameKo(), brandVO.getNameEng());

        ParameterUtil.checkParameterEmpty(brandVO.getNameKo(), brandVO.getNameEng());
        brandService.modifyBrand(brandId, brandVO);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "브랜드 삭제", notes = "브랜드를 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "삭제 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 브랜드"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping(value = "/{brand_id}")
    private ResponseVO removeBrand(@PathVariable(value = "brand_id") Long brandId) {
        log.debug("removeBrand id {}", brandId);

        brandService.removeBrand(brandId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "브랜드 리스트 조회", notes = "브랜드 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping
    private DataListResponseVO<BrandVO> readBrands() {
        log.debug("readBrands");

        List<BrandVO> brands = brandService.readBrands();
        return new DataListResponseVO<BrandVO>(brands);
    }
}
