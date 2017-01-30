package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.Zzim;
import kr.co.mash_up.nine_tique.domain.ZzimProduct;
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.dto.ProductImageDto;
import kr.co.mash_up.nine_tique.dto.ShopDto;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.ZzimRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
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
     * Todo: col 추가해서 enable시키기
     * 찜하기
     *
     * @param userId    요청한 유저 id
     * @param productId 찜할 상품 id
     * @return 성공/실패 여부
     */
    @Transactional
    public void addProduct(Long userId, Long productId) {
        Zzim zzim = zzimRepository.findOne(userId);  // query count down 가능
        Product product = productRepository.findOne(productId);

        Optional.ofNullable(product).orElseThrow(() -> new IdNotFoundException("zzim add product -> product not found"));

        ZzimProduct zzimProduct = new ZzimProduct(zzim, product);
        if (checkProductZzim(zzim.getZzimProducts(), productId)) {
            throw new AlreadyExistException("zzim add product -> zzim already exist");
        }
        zzim.getZzimProducts().add(zzimProduct);
    }


    /**
     * Todo: col 추가해서 disable시키기
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

        ZzimProduct zzimProduct = new ZzimProduct(zzim, product);
        if (!checkProductZzim(zzim.getZzimProducts(), productId)) {
            throw new IdNotFoundException("zzim delete product -> zzim not found");
        }

        int deleteItemPosition = searchProductZzimIndex(zzim.getZzimProducts(), zzimProduct);
        zzim.getZzimProducts().remove(deleteItemPosition);
    }

    /**
     * 찜된 목록에서 삭제할 상품 위치 찾기
     *
     * @param zzimProducts 찜된 상품 목록
     * @param zzimProduct  찾을 상품
     * @return 리스트의 상품 위치
     */
    private int searchProductZzimIndex(List<ZzimProduct> zzimProducts, ZzimProduct zzimProduct) {
        for (int idx = 0; idx < zzimProducts.size(); idx++) {
            ZzimProduct.Id currentZzimProductId = zzimProducts.get(idx).getId();
            if (Objects.equals(zzimProduct.getId().getProductId(), currentZzimProductId.getProductId())
                    && Objects.equals(zzimProduct.getId().getZzimId(), currentZzimProductId.getZzimId())) {
                return idx;
            }
        }
        return -1;
    }

    /**
     * 찜되어 있는지 확인
     *
     * @param zzimProducts 찜된 상품 목록
     * @param productId    확인할 상품 id
     * @return 결과
     */
    private boolean checkProductZzim(List<ZzimProduct> zzimProducts, Long productId) {
        for (ZzimProduct zzimProduct : zzimProducts) {
            if (Objects.equals(zzimProduct.getProduct().getId(), productId)) {
                return true;
            }
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

        Optional.ofNullable(zzimProductPage).orElseThrow(() ->  new IdNotFoundException("zzim products not found"));

        // DTO로 변환
        List<ProductDto> productDtos = zzimProductPage.getContent().stream()
                .map(zzimProduct -> {
                    Product product = zzimProduct.getProduct();

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
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(zzimProductPage.getNumber(), zzimProductPage.getSize(),
                new Sort(Sort.Direction.DESC, "createdAt"));

        return new PageImpl<ProductDto>(productDtos, resultPageable, zzimProductPage.getTotalElements());
    }
}
