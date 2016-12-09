package kr.co.mash_up.nine_tique.controller;

import kr.co.mash_up.nine_tique.domain.*;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.repository.ProductImageRepository;
import kr.co.mash_up.nine_tique.repository.SellerInfoRepository;
import kr.co.mash_up.nine_tique.service.ProductService;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseVO add(@RequestParam(name = "name") String name,
//                          @RequestParam(name = "brandName") String brandName,
//                          @RequestParam(name = "size") String size,
//                          @RequestParam(name = "price") int price,
//                          @RequestParam(name = "description") String description,
//                          @RequestParam(name = "productStatus") String productStatus,
//                          @RequestParam(name = "sellerId") long sellerId,
//                          @RequestParam(name = "mainCategory") String mainCategory,
//                          @RequestParam(name = "subCategory") String subCategory,
//                          @RequestParam(name = "files") List<MultipartFile> files
//                          ) {
////        ParameterUtil.checkParameterEmpty(requestVO.getName());  Todo: field setting
//
//        Product product = new Product();
//        product.setName(name);
//        product.setBrandName(brandName);
//        product.setSize(size);
//        product.setPrice(price);
//        product.setDescription(description);
//        SellerInfo sellerInfo = sellerInfoRepository.findOne(sellerId);
//        product.setSellerInfo(sellerInfo);
//        Category category = categoryRepository.findByMainAndSubAllIgnoreCase(mainCategory, subCategory);
//        product.setCategory(category);
//
//        if(productStatus.equals("SELL")){
//            product.setProductStatus(ProductStatus.SELL);
//        }else if(productStatus.equals("SOLD_OUT")){
//            product.setProductStatus(ProductStatus.SOLD_OUT);
//        }
//
//        Product savedProduct = productService.save(product);
//
//        for(MultipartFile file : files){
////            ParameterUtil.checkParameterEmpty(file);
//            if(file != null && !file.isEmpty()){
//                ProductImage productImage = new ProductImage();
//
//                String saveName = UUID.randomUUID().toString() +
//                        file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//
//                log.debug(saveName);
//
//                productImage.setFileName(saveName);
//                productImage.setOriginalFileName(file.getOriginalFilename());
//                productImage.setSize(file.getSize());
//
//                productImage.setProduct(savedProduct);
//                FileUtil.upload(file, productImage.getImageUploadPath(), saveName);
//
////                FileUtil.upload(file, FileUtil.getImageUploadPath(savedProduct.getId()), saveName);
////                productImage.setImageUrl(FileUtil.getImageUrl(savedProduct.getId(), saveName));
//
//                productImageRepository.save(productImage);
//
//                log.debug(file.getOriginalFilename());
//            }
//        }
//
//
//        if (savedProduct != null) {
//            return ResponseVO.ok();
//        }
//
//        //Todo: 생성 실패시 예외처리
//        return ResponseVO.ok();
//    }

    /**
     * 상품 생성
     *
     * @param requestVO 생성할 상품 정보
     * @return 생성 결과
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseVO add(@RequestBody ProductRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getBrandName(), requestVO.getSize(),
                requestVO.getPrice(), requestVO.getDescription(), requestVO.getProductStatus(), requestVO.getSellerId(),
                requestVO.getMainCategory(), requestVO.getFiles());

        Product product = productService.save(requestVO);

        if (product != null) {
            return ResponseVO.ok();
        }

        //Todo: 생성 실패시 예외처리
        return ResponseVO.ok();
    }

    /**
     * 상품 정보 수정 (상태변경 - 판매중/완료)
     * @param id 수정할 상품 id
     * @param requestVO 수정할 상품 정보
     * @return 수정 결과
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseVO update(@PathVariable Long id, @RequestBody ProductRequestVO requestVO) {
        // 바뀐 정보만 update하기 위해 service단에서 체크한다.
//        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getBrandName(), requestVO.getSize(),
//                requestVO.getPrice(), requestVO.getDescription(), requestVO.getProductStatus(), requestVO.getSellerId(),
//                requestVO.getMainCategory(), requestVO.getFiles());

        Product product = productService.update(id, requestVO);

        if (product != null) {
            return ResponseVO.ok();
        }

        //Todo: 업데이트 실패시 예외처리
        return ResponseVO.ok();
    }

    /**
     * 상품 상세정보 조회
     *
     * @param id 상세조회할 상품 id
     * @return 조회한 상품 정보
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DataResponseVO<Product> detail(@PathVariable Long id) {
        Product product = productService.findOne(id);
        return new DataResponseVO<Product>(product);
    }


    /**
     * 카테고리별 리스트 조회
     *
     * @param requestVO request parameter가 담긴 VO
     * @return 카테고리별 상품 리스트
     */
    @RequestMapping(method = RequestMethod.GET)
    public DataListResponseVO<Product> list(ProductListRequestVO requestVO) {

        Page<Product> page = productService.findProductsByCategory(requestVO);

        log.debug(requestVO.getPage() + " " + requestVO.getPageSize() + " " + requestVO.getPageable() +
                " " + requestVO.getMainCategory() + " " + requestVO.getSubCategory());

        return new DataListResponseVO<Product>(page);
    }

    /**
     * 상품 삭제
     *
     * @param id 삭제할 상품 id
     * @return 삭제 결과
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseVO delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseVO.ok();
    }

    /*
   찜 - 하기, 해제,
   유저의 찜테이블에 상품 id 추가
   유저정보, 상품 id
    */
    @RequestMapping(value = "/{id}/zzim", method = RequestMethod.PUT)
    public void zzim() {

    }
}
