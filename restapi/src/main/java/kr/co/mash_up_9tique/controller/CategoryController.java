package kr.co.mash_up_9tique.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.mash_up_9tique.domain.Category;
import kr.co.mash_up_9tique.service.CategorySservice;
import kr.co.mash_up_9tique.util.ParameterUtil;
import kr.co.mash_up_9tique.vo.CategoryRequestVO;
import kr.co.mash_up_9tique.vo.DataListResponseVO;
import kr.co.mash_up_9tique.vo.DataResponseVO;
import kr.co.mash_up_9tique.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kr.co.mash_up_9tique.util.Constant.RestEndpoint.API_CATEGORY;


@RestController
@RequestMapping(value = API_CATEGORY)
//자동으로 logging을 위한 필드인 ‘private static final Logger log’가 추가, Slf4j를 사용하여 출력
@Slf4j
@Api(description = "카테고리", tags = {"category"})
public class CategoryController {

    @Autowired
    private CategorySservice categorySservice;

    /**
     * 카테고리 생성
     *
     * @param requestVO 생성할 카테고리 정보
     * @return 생성 결과
     */
    @ApiOperation(value = "카테고리 생성")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseVO add(@RequestBody CategoryRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getMain(), requestVO.getSub());
        log.debug(requestVO.getMain() + ", " + requestVO.getSub());
        Category category = categorySservice.create(requestVO);

        if (category != null) {  // 생성 성공
            return ResponseVO.ok();
        }

        //Todo: 생성 실패시 예외처리 -> 어떤 경우가 있을까..?
        return ResponseVO.ok();
    }

    /**
     * 카테고리 수정
     *
     * @param id        수정할 카테고리 id
     * @param requestVO 수정할 내용
     * @return 수정 결과
     */
    @ApiOperation(value = "카테고리 수정")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseVO update(@PathVariable Long id, @RequestBody CategoryRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getMain(), requestVO.getSub());
        Category category = categorySservice.update(id, requestVO.toCategoryEntity());

        if (category != null) {  // 수정 성공
            return ResponseVO.ok();
        }

        //Todo: 수정 실패시 예외처리 -> 어떤 경우가 있을까..?
        return ResponseVO.ok();
    }

    /**
     * 카테고리 삭제
     *
     * @param id 삭제할 카테고리 id
     * @return 삭제 결과
     */
    @ApiOperation(value = "카테고리 삭제")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseVO delete(@PathVariable Long id) {
        categorySservice.delete(id);
        return ResponseVO.ok();
    }

    /**
     * 카테고리 리스트 조회
     *
     * @return 카테고리 목록
     */
    @ApiOperation(value = "카테고리 리스트 조회")
    @RequestMapping(method = RequestMethod.GET)
    public DataListResponseVO<Category> list() {
        List<Category> categories = categorySservice.findCategories();
        return new DataListResponseVO<Category>(categories);
    }

    /**
     * 카테고리 상세정보 조회
     *
     * @param categoryId 상세조회할 카테고리 id
     * @return 조회한 카테고리 정보
     */
    @ApiOperation(value = "카테고리 상세정보 조회")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DataResponseVO<Category> detail(@PathVariable("id") Long categoryId) {
        Category category = categorySservice.findById(categoryId);
        return new DataResponseVO<Category>(category);
    }
}
