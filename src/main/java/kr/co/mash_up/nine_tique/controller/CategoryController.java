package kr.co.mash_up.nine_tique.controller;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.service.CategorySservice;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.CategoryRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import kr.co.mash_up.nine_tique.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
//자동으로 logging을 위한 필드인 ‘private static final Logger log’가 추가, Slf4j를 사용하여 출력
@Slf4j
public class CategoryController {

    @Autowired
    private CategorySservice categorySservice;

    /**
     * 카테고리 생성
     * @param requestVO 생성할 카테고리 정보
     * @return 생성 결과
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseVO add(@RequestBody CategoryRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getMain(), requestVO.getSub());
        log.debug(requestVO.getMain() + ", " + requestVO.getSub());

        Category category = categorySservice.save(requestVO.toCategoryEntity());

        if (category != null) {
            return ResponseVO.ok();
        }

        //Todo: 생성 실패시 예외처리
       return ResponseVO.ok();
    }

    /**
     * 카테고리 수정
     * @param id 수정할 카테고리 id
     * @param requestVO 수정할 내용
     * @return 수정 결과
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DataResponseVO<Category> update(@PathVariable Long id, @RequestBody CategoryRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getMain(), requestVO.getSub());
        Category category = categorySservice.update(id, requestVO.toCategoryEntity());
        return new DataResponseVO<Category>(category);
    }

    /**
     * 카테고리 삭제
     * @param id 삭제할 카테고리 id
     * @return 삭제 결과
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseVO delete(@PathVariable Long id) {
        categorySservice.delete(id);
        return ResponseVO.ok();
    }

    /**
     * 카테고리 목록 조회
     * @return 카테고리 목록
     */
    @RequestMapping(method = RequestMethod.GET)
    public DataListResponseVO<Category> list() {
        List<Category> categories = categorySservice.findCategories();
        return new DataListResponseVO<Category>(categories);
    }

    /**
     * 카테고리 상세정보 조회
     * @param id 상세조회할 카테고리 id
     * @return 조회한 카테고리 정보
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DataResponseVO<Category> detail(@PathVariable Long id) {
        Category category = categorySservice.findOne(id);
        return new DataResponseVO<Category>(category);
    }
}
