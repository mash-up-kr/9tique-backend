package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.Image;
import kr.co.mash_up.nine_tique.domain.ImageType;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.ProductImage;
import kr.co.mash_up.nine_tique.domain.Zzim;
import kr.co.mash_up.nine_tique.domain.ZzimProduct;
import kr.co.mash_up.nine_tique.dto.ImageDto;
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.dto.ShopDto;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.ZzimRepository;
import kr.co.mash_up.nine_tique.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Zzim과 관련된 비즈니스 로직 처리
 */
@Service(value = "zzimService")
@Slf4j
public class ZzimService {

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
    public void addProduct(Long userId, Long productId) {
        Zzim zzim = zzimRepository.findOne(userId);  // query count down 가능
        Product product = productRepository.findOneByProductId(productId);

        Optional.ofNullable(product).orElseThrow(() -> new IdNotFoundException("zzim add product -> product not found"));

        ZzimProduct oldZzimProduct = zzimRepository.getZzimProduct(zzim, product);
        if (oldZzimProduct == null) {  // 1번도 등록 안한 경우 등록
            zzim.addZzimProduct(new ZzimProduct(zzim, product));
            return;
        }

        if (oldZzimProduct.isActive()) { // 1번 등록했고, enable되있을 경우
            throw new AlreadyExistException("zzim add product -> zzim already exist");
        }
        // 1번 등록했고, disable되있을 경우
        zzim.addZzimProduct(oldZzimProduct);  // 등록
    }

    /**
     * 찜해제
     *
     * @param userId    요청한 유저 id
     * @param productId 찜 해제할 상품 id
     * @return 성공/실패 여부
     */
    @Transactional
    public void deleteProduct(Long userId, Long productId) {
        Zzim zzim = zzimRepository.findOne(userId);
        Product product = productRepository.findOne(productId);

        Optional.ofNullable(product).orElseThrow(() -> new IdNotFoundException("zzim delete product -> product not found"));

        ZzimProduct oldZzimProduct = zzimRepository.getZzimProduct(zzim, product);
        if (oldZzimProduct == null) {
            throw new IdNotFoundException("zzim delete product -> zzim not found");
        }

        zzim.removeZzimProduct(oldZzimProduct);
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

        Optional.ofNullable(zzimProductPage).orElseThrow(() -> new IdNotFoundException("zzim products not found"));

        // DTO로 변환
        List<ProductDto> productDtos = zzimProductPage.getContent().stream()
                .map(zzimProduct -> {
                    Product product = zzimProduct.getProduct();

                    List<ImageDto> productImageDtos = product.getProductImages().stream()
                            .map(ProductImage::getImage)
                            .sorted(Comparator.comparingLong(Image::getId))
                            .map(productImage ->
                                    new ImageDto.Builder()
                                            .url(FileUtil.getImageUrl(ImageType.PRODUCT, product.getId(), productImage.getFileName()))
                                            .build()).collect(Collectors.toList());

                    ShopDto shopDto = new ShopDto.Builder()
                            .name(product.getShop().getName())
                            .description(product.getShop().getDescription())
                            .phoneNumber(product.getShop().getPhoneNumber())
                            .build();

                    return new ProductDto.Builder()
                            .withId(product.getId())
                            .withName(product.getName())
                            .withBrandName(product.getBrand().getNameKo())
                            .withSize(product.getSize())
                            .withPrice(product.getPrice())
                            .withDescription(product.getDescription())
                            .withStatus(product.getStatus())
                            .withMainCategory(product.getCategory().getMain())
                            .withSubCategory(product.getCategory().getSub())
                            .withShop(shopDto)
                            .withImages(productImageDtos)
                            .withZzimStatus(true)
                            .withCreatedAt(product.getCreatedTimestamp())
                            .withUpdatedAt(product.getUpdatedTimestamp())
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(zzimProductPage.getNumber(), zzimProductPage.getSize(),
                new Sort(Sort.Direction.DESC, "createdAt"));

        return new PageImpl<ProductDto>(productDtos, resultPageable, zzimProductPage.getTotalElements());
    }
}
