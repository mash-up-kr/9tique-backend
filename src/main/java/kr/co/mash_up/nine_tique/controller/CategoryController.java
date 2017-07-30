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
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.service.impl.CategoryServiceImpl;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.CategoryRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_CATEGORY;

@RestController
@RequestMapping(value = API_CATEGORY)
//자동으로 logging을 위한 필드인 ‘private static final Logger log’가 추가, Slf4j를 사용하여 출력
@Slf4j
@Api(description = "카테고리", tags = {"category"})
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * 카테고리 생성
     *
     * @param requestVO 생성할 카테고리 정보
     * @return 생성 결과
     */
    @ApiOperation(value = "카테고리 생성")
    @PostMapping
    public ResponseVO createCategory(@RequestBody CategoryRequestVO requestVO) {
        log.info("createCategory - category : {}", requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getMain(), requestVO.getSub());
        categoryService.addCategory(requestVO);
        return ResponseVO.ok();
    }

    /**
     * 카테고리 수정
     *
     * @param categoryId 수정할 카테고리 id
     * @param requestVO  수정할 내용
     * @return 수정 결과
     */
    @ApiOperation(value = "카테고리 수정")
    @PutMapping(value = "/{category_id}")
    public ResponseVO updateCategory(@PathVariable(value = "category_id") Long categoryId, @RequestBody CategoryRequestVO requestVO) {
        log.info("updateCategory - categoryId : {}, category : {}", categoryId, requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getMain(), requestVO.getSub());
        categoryService.modifyCategory(categoryId, requestVO);
        return ResponseVO.ok();
    }

    /**
     * 카테고리 삭제
     *
     * @param categoryId 삭제할 카테고리 id
     * @return 삭제 결과
     */
    @ApiOperation(value = "카테고리 삭제")
    @DeleteMapping(value = "/{category_id}")
    public ResponseVO deleteCategory(@PathVariable(value = "category_id") Long categoryId) {
        log.info("deleteCategory - categoryId : {}", categoryId);

        categoryService.removeCategory(categoryId);
        return ResponseVO.ok();
    }

    /**
     * 카테고리 리스트 조회
     *
     * @return 카테고리 목록
     */
    @ApiOperation(value = "카테고리 리스트 조회")
    @GetMapping
    public DataListResponseVO<Category> readCategories() {
        log.info("readCategories");

        List<Category> categories = categoryService.readCategories();
        return new DataListResponseVO<Category>(categories);
    }

    /**
     * 카테고리 상세정보 조회
     *
     * @param categoryId 상세조회할 카테고리 id
     * @return 조회한 카테고리 정보
     */
    @ApiOperation(value = "카테고리 상세정보 조회")
    @GetMapping(value = "/{category_id}")
    public DataResponseVO<Category> readCategory(@PathVariable("category_id") Long categoryId) {
        log.info("readCategory categoryId : {}", categoryId);

        Category category = categoryService.readCategory(categoryId);
        return new DataResponseVO<>(category);
    }
}
