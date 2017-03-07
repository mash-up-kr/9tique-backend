package kr.co.mash_up.nine_tique.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.mash_up.nine_tique.dto.ProductImageDto;
import kr.co.mash_up.nine_tique.service.ProductImageService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.DataListResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_PRODUCT_IMAGE;

@RestController
@RequestMapping(value = API_PRODUCT_IMAGE)
@Api(description = "상품 이미지", tags = {"product_image"})
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @ApiOperation(value = "상품 이미지 생성")
    @PostMapping(value = "")
    public DataListResponseVO<ProductImageDto> add(@RequestParam(name = "files") List<MultipartFile> files) {
        ParameterUtil.checkParameterEmpty(files);

        List<ProductImageDto> productImageDtos = productImageService.create(files);

        return new DataListResponseVO<ProductImageDto>(productImageDtos);
    }
}