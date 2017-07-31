package kr.co.mash_up.nine_tique.web.controller;

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
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.service.impl.CategoryServiceImpl;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.web.vo.CategoryRequestVO;
import kr.co.mash_up.nine_tique.web.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.web.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.web.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_CATEGORY;

/**
 * 카테고리와 관련된 request를 처리한다
 * <p>
 * Created by ethankim on 2016. 10. 22..
 */
@RestController
@RequestMapping(value = API_CATEGORY)
//자동으로 logging을 위한 필드인 ‘private static final Logger log’가 추가, Slf4j를 사용하여 출력
@Slf4j
@Api(description = "카테고리", tags = {"category"})
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @ApiOperation(value = "카테고리 추가", notes = "카테고리 정보를 추가한다")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "추가 성공"),
            @ApiResponse(code = 400, message = "이미 존재하는 카테고리"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping
    public ResponseVO addCategory(@RequestBody CategoryRequestVO requestVO) {
        log.info("addCategory - category : {}", requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getMain(), requestVO.getSub());
        categoryService.addCategory(requestVO);
        return ResponseVO.created();
    }

    @ApiOperation(value = "카테고리 수정", notes = "카테고리 정보를 수정한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 카테고리"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PutMapping(value = "/{category_id}")
    public ResponseVO modifyCategory(@PathVariable(value = "category_id") Long categoryId, @RequestBody CategoryRequestVO requestVO) {
        log.info("modifyCategory - categoryId : {}, category : {}", categoryId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getMain(), requestVO.getSub());
        categoryService.modifyCategory(categoryId, requestVO);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "카테고리 삭제", notes = "카테고리 정보를 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "수정 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 카테고리"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @DeleteMapping(value = "/{category_id}")
    public ResponseVO removeCategory(@PathVariable(value = "category_id") Long categoryId) {
        log.info("removeCategory - categoryId : {}", categoryId);

        categoryService.removeCategory(categoryId);
        return ResponseVO.ok();
    }

    @ApiOperation(value = "카테고리 리스트 조회", notes = "카테고리 리스트를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping
    public DataListResponseVO<Category> readCategories() {
        log.info("readCategories");

        List<Category> categories = categoryService.readCategories();
        return new DataListResponseVO<>(categories);
    }

    @ApiOperation(value = "카테고리 상세정보 조회", notes = "카테고리 상세정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 카테고리"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping(value = "/{category_id}")
    public DataResponseVO<Category> readCategory(@PathVariable("category_id") Long categoryId) {
        log.info("readCategory categoryId : {}", categoryId);

        Category category = categoryService.readCategory(categoryId);
        return new DataResponseVO<>(category);
    }
}
