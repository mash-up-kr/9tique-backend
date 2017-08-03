package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.domain.Image;
import kr.co.mash_up.nine_tique.domain.ImageType;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.ProductImage;
import kr.co.mash_up.nine_tique.domain.Zzim;
import kr.co.mash_up.nine_tique.domain.ZzimProduct;
import kr.co.mash_up.nine_tique.web.dto.BrandDto;
import kr.co.mash_up.nine_tique.web.dto.ImageDto;
import kr.co.mash_up.nine_tique.web.dto.ProductDto;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.ZzimRepository;
import kr.co.mash_up.nine_tique.service.ZzimService;
import kr.co.mash_up.nine_tique.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ZzimServiceImpl implements ZzimService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ZzimRepository zzimRepository;

    @Transactional
    @Override
    public void addZzimProduct(Long userId, Long productId) {
        Zzim zzim = zzimRepository.findOne(userId);  // query count down 가능
        Optional<Product> productOptional = productRepository.findOneByProductId(productId);
        Product product = productOptional.orElseThrow(() -> new IdNotFoundException("addZzimProduct -> product not found"));

        zzim.addZzimProduct(new ZzimProduct(zzim, product));
        zzimRepository.save(zzim);
    }

    @Transactional
    @Override
    public void removeZzimProduct(Long userId, Long productId) {
        Zzim zzim = zzimRepository.findOne(userId);
        Optional<ZzimProduct> zzimProductOptional = zzimRepository.findZzimProduct(userId, productId);
        ZzimProduct zzimProduct = zzimProductOptional.orElseThrow(() -> new IdNotFoundException("removeZzimProduct -> product not found"));
        zzim.removeZzimProduct(zzimProduct);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> readZzimProducts(Long userId, Pageable pageable) {
        Page<ZzimProduct> zzimProductPage = zzimRepository.findZzimProducts(userId, pageable);

        // DTO로 변환
        List<ProductDto> productDtos = zzimProductPage.getContent().stream()
                .map(zzimProduct -> {
                    Product product = zzimProduct.getProduct();

                    List<ImageDto> productImages = product.getProductImages().stream()
                            .map(ProductImage::getImage)
                            .sorted(Comparator.comparingLong(Image::getId))
                            .map(productImage ->
                                    new ImageDto.Builder()
                                            .url(FileUtil.getImageUrl(ImageType.PRODUCT, product.getId(), productImage.getFileName()))
                                            .build()).collect(Collectors.toList());

//                    ShopDto shopDto = new ShopDto.Builder()
//                            .name(product.getShop().getName())
//                            .description(product.getShop().getDescription())
//                            .phoneNumber(product.getShop().getPhoneNumber())
//                            .build();

                    return new ProductDto.Builder()
                            .withId(product.getId())
                            .withName(product.getName())
                            .withBrand(BrandDto.fromBrand(product.getBrand()))
                            .withSize(product.getSize())
                            .withPrice(product.getPrice())
//                            .withDescription(product.getDescription())
                            .withStatus(product.getStatus())

//                            .withCategory(CategoryDto.fromCategory(product.getCategory()))
//                            .withShop(shopDto)
                            .withImages(productImages)
                            .withZzimStatus(true)
//                            .withCreatedAt(product.getCreatedTimestamp())
//                            .withUpdatedAt(product.getUpdatedTimestamp())
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(zzimProductPage.getNumber(), zzimProductPage.getSize(),
                new Sort(Sort.Direction.DESC, "createdAt"));

        return new PageImpl<>(productDtos, resultPageable, zzimProductPage.getTotalElements());
    }
}
