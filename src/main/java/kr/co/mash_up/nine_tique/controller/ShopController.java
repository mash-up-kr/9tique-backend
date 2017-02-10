package kr.co.mash_up.nine_tique.controller;

import kr.co.mash_up.nine_tique.domain.Shop;
import kr.co.mash_up.nine_tique.dto.ShopDto;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.ShopService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/shops")
@Slf4j
public class ShopController {

    @Autowired
    private ShopService shopService;

    /**
     * 매장 정보 등록
     *
     * @param requestVO 등록할 매장 정보
     * @return 등록 결과
     */
    @PostMapping
    public ResponseVO create(@RequestBody ShopRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getInfo(), requestVO.getPhone());
        log.info("shop vo: " + requestVO);

        Shop shop = shopService.create(requestVO);

        if (shop != null) {  // 생성 성공
            return ResponseVO.ok();
        }

        //Todo: 생성 실패시 예외처리 -> 어떤 경우가 있을까..?
        return ResponseVO.ok();
    }

    /**
     * 매장 목록 조회
     *
     * @param requestVO 페이지 정보
     * @return 매장 목록
     */
    @GetMapping(value = "")
    public DataListResponseVO<ShopDto> list(DataListRequestVO requestVO) {
        log.debug(requestVO.getPageNo() + " " + requestVO.getPageSize() + " " + requestVO.getPageable());

        Page<ShopDto> page = shopService.list(requestVO);

        return new DataListResponseVO<ShopDto>(page);
    }

    /**
     * 매장 상세정보 조회
     *
     * @param shopId 상세조회할 매장 id
     */
    @GetMapping(value = "/{id}")
    public DataResponseVO<ShopDto> detail(@PathVariable("id") Long shopId) {
        ShopDto shopDto = shopService.findOne(shopId);
        return new DataResponseVO<ShopDto>(shopDto);
    }

    /**
     * 매장 정보 수정
     *
     * @param shopId    수정할 매장 id
     * @param requestVO 수정할 정보
     * @return 수정 결과
     */
    @PutMapping(value = "/{id}")
    public ResponseVO update(@PathVariable("id") Long shopId, @RequestBody ShopRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getInfo(), requestVO.getPhone());
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("userId: " + userId);
        log.info("shop vo: " + requestVO);

        Shop shop = shopService.update(userId, shopId, requestVO);

        if (shop != null) {  // 생성 성공
            return ResponseVO.ok();
        }

        //Todo: 수정 실패시 예외처리 -> 어떤 경우가 있을까..?
        return ResponseVO.ok();
    }

    /**
     * 매장 정보 삭제
     *
     * @param shopId 삭제할 매장 id
     * @return 삭제 결과
     */
    @DeleteMapping(value = "/{id}")
    public ResponseVO delete(@PathVariable("id") Long shopId) {
        shopService.delete(shopId);
        return ResponseVO.ok();
    }
}
