package kr.co.mash_up.nine_tique.controller;


import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.SellerService;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public DataListResponseVO<ProductDto> list(DataListRequestVO requestVO) {
        Long userId = SecurityUtil.getCurrentUser().getId();

        Page<ProductDto> page = sellerService.findProducts(userId, requestVO.getPageable());
        return new DataListResponseVO<ProductDto>(page);
    }
}
