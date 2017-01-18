package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.SellerProduct;
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.dto.ProductImageDto;
import kr.co.mash_up.nine_tique.dto.ShopDto;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Seller와 관련된 비즈니스 로직 처리
 */
@Service(value = "sellerService")
@Slf4j
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Transactional(readOnly = true)
    public Page<ProductDto> findProducts(Long userId, Pageable pageable) {
        Page<SellerProduct> sellerProductPage = sellerRepository.getSellerProducts(userId, pageable);

        if (sellerProductPage == null) {
            throw new IdNotFoundException("selle products not found");
        }

        List<ProductDto> productDtos = sellerProductPage.getContent().stream()
                .map(sellerProduct -> {
                    Product product = sellerProduct.getProduct();

                    List<ProductImageDto> productImageDtos = product.getProductImages().stream()
                            .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                            .map(productImage -> {
                                return new ProductImageDto.Builder()
                                        .withUrl(productImage.getImageUrl())
                                        .build();
                            }).collect(Collectors.toList());

                    ShopDto shopDto = new ShopDto.Builder()
                            .withName(product.getShop().getName())
                            .withInfo(product.getShop().getInfo())
                            .withPhone(product.getShop().getPhone())
                            .build();

                    return new ProductDto.Builder()
                            .withId(product.getId())
                            .withName(product.getName())
                            .withBrandName(product.getBrandName())
                            .withSize(product.getSize())
                            .withPrice(product.getPrice())
                            .withDescription(product.getDescription())
                            .withStatus(product.getStatus())
                            .withMainCategory(product.getCategory().getMain())
                            .withSubCategory(product.getCategory().getSub())
                            .withShop(shopDto)
                            .withProductImages(productImageDtos)
                            /*
                             현재 요구사항으로 판매자는 찜기능이 없다.
                             요구사항의 변경으로 필요할지도 모르니 주석처리
                                .withZzimStatus(isZzim)
                              */
                            .withCreatedAt(product.getCreatedTimestamp())
                            .withUpdatedAt(product.getUpdatedTimestamp())
                            .withSeller(true)  // 판매자가 등록한 상품 목록이므로 true
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(sellerProductPage.getNumber(), sellerProductPage.getSize(),
                new Sort(Sort.Direction.DESC, "createdAt"));

        return new PageImpl<ProductDto>(productDtos, resultPageable, sellerProductPage.getTotalElements());
    }
}
