package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.*;
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.dto.ProductImageDto;
import kr.co.mash_up.nine_tique.dto.SellerInfoDto;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import kr.co.mash_up.nine_tique.repository.ZzimRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Zzim과 관련된 비즈니스 로직 처리
 */
@Service
@Slf4j
public class ZzimService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ZzimRepository zzimRepository;

    /**
     * 찜하기
     *
     * @param userId    요청한 유저 id
     * @param productId 찜할 상품 id
     * @return 성공/실패 여부
     */
    @Transactional
    public boolean addProduct(Long userId, Long productId) {
        User user = userRepository.findOne(userId);
        Zzim zzim = user.getZzim();
//        Zzim zzim = zzimRepository.findOne(userId);  // query count down 가능

        Product product = productRepository.findOne(productId);
        ZzimProduct zzimProduct = new ZzimProduct(zzim, product);

        if (!zzim.getZzimProducts().containsKey(zzimProduct.getId())) {
            zzim.getZzimProducts().put(zzimProduct.getId(), zzimProduct);
            return true;
        }
        return false;
    }

    /**
     * 찜해제
     *
     * @param userId    요청한 유저 id
     * @param productId 찜 해제할 상품 id
     * @return 성공/실패 여부
     */
    @Transactional
    public boolean deleteProduct(Long userId, Long productId) {
        User user = userRepository.findOne(userId);
        Zzim zzim = user.getZzim();
//        Zzim zzim = zzimRepository.findOne(userId);  // query count down 가능

        Product product = productRepository.findOne(productId);
        ZzimProduct zzimProduct = new ZzimProduct(zzim, product);

        if (zzim.getZzimProducts().containsKey(zzimProduct.getId())) {
            zzim.getZzimProducts().remove(zzimProduct.getId());
            return true;
        }
        return false;
    }

    /**
     * 찜 목록 조회
     *
     * @param userId   요청한 유저 id
     * @param pageable page 정보
     * @return 목록
     */
    @Transactional(readOnly = true)
    public Page<ProductDto> findZzimProducts(Long userId, Pageable pageable) {
        Page<ZzimProduct> zzimProductPage = zzimRepository.getZzimProducts(userId, pageable);

        // DTO로 변환
        List<ProductDto> productDtos = zzimProductPage.getContent().stream()
                .map(zzimProduct -> {
                    Product product = zzimProduct.getProduct();

                    List<ProductImageDto> productImageDtos = new ArrayList<>();
                    for (ProductImage image : product.getProductImages()) {
                        productImageDtos.add(new ProductImageDto.Builder()
                                .withUrl(image.getImageUrl())
                                .build());
                    }

                    SellerInfoDto sellerInfoDto = new SellerInfoDto.Builder()
                            .withShopName(product.getSellerInfo().getShopName())
                            .withShopInfo(product.getSellerInfo().getShopInfo())
                            .withPhone(product.getSellerInfo().getPhone())
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
                            .withSellerInfo(sellerInfoDto)
                            .withProductImages(productImageDtos)
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(zzimProductPage.getNumber(), zzimProductPage.getSize(),
                new Sort(Sort.Direction.DESC, "createdAt"));

        return new PageImpl<ProductDto>(productDtos, resultPageable, zzimProductPage.getTotalElements());
    }
}
