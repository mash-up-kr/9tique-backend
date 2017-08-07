package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.domain.Brand;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Image;
import kr.co.mash_up.nine_tique.domain.ImageType;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.ProductImage;
import kr.co.mash_up.nine_tique.domain.SellerProduct;
import kr.co.mash_up.nine_tique.domain.ZzimProduct;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.BrandRepository;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.SellerRepository;
import kr.co.mash_up.nine_tique.repository.ZzimRepository;
import kr.co.mash_up.nine_tique.service.BrandService;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.web.dto.BrandDto;
import kr.co.mash_up.nine_tique.web.dto.ImageDto;
import kr.co.mash_up.nine_tique.web.dto.ProductDto;
import kr.co.mash_up.nine_tique.web.vo.BrandRequestVO;
import kr.co.mash_up.nine_tique.web.vo.ProductListRequestVO;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by ethankim on 2017. 7. 3..
 */
@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ZzimRepository zzimRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Transactional
    @Override
    public void addBrand(BrandRequestVO brandVO) {
        Optional<Brand> brand = brandRepository.findByNameKo(brandVO.getNameKo());
        brand.orElseThrow(() -> new AlreadyExistException("brand add -> brand already exist"));
        brandRepository.save(brandVO.toBrandEntity());
    }

    @Transactional
    @Override
    public void modifyBrand(Long brandId, BrandRequestVO brandVO) {
        Brand oldBrand = brandRepository.findOne(brandId);
        Optional.ofNullable(oldBrand).orElseThrow(() -> new IdNotFoundException("brand modify -> brand not found"));
        oldBrand.update(brandVO.toBrandEntity());
        brandRepository.save(oldBrand);
    }

    @Transactional
    @Override
    public void removeBrand(Long brandId) {
        Brand brand = brandRepository.findOne(brandId);
        Optional.ofNullable(brand).orElseThrow(() -> new IdNotFoundException("brand remove -> brand not found"));
        brandRepository.delete(brandId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BrandDto> readBrands() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream()
                .map(brand -> new BrandDto.Builder()
                        .brandId(brand.getId())
                        .nameKo(brand.getNameKo())
                        .nameEng(brand.getNameEng())
                        .build()
                ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductDto> readBrandProducts(Long brandId, Long userId, ProductListRequestVO requestVO) {
        Pageable pageable = requestVO.getPageable();
        String mainCategory = requestVO.getMainCategory().toUpperCase();
        String subCategory = requestVO.getSubCategory().toUpperCase();

        Page<Product> productPage;

        if (mainCategory.equalsIgnoreCase("NEW")) {
            productPage = productRepository.findBrandProducts(brandId, pageable);
        } else {
            if (subCategory.equalsIgnoreCase("ALL")) {
                productPage = productRepository.findBrandProductsByMainCategory(brandId, mainCategory, pageable);
            } else {
                Optional<Category> categoryOptional = categoryRepository.findOneByMainAndSub(mainCategory, subCategory);
                Category category = categoryOptional.orElseThrow(() -> new IdNotFoundException("find product by category -> category not found"));

                log.debug(category.getMain() + " " + category.getSub() + " " + category.getId());

                productPage = productRepository.findBrandProductsByCategory(brandId, category, pageable);
            }
        }

        List<ZzimProduct> zzimProducts = zzimRepository.findZzimProducts(userId);
        List<SellerProduct> sellerProducts = sellerRepository.findSellerProducts(userId);

        List<ProductDto> productDtos = productPage.getContent().stream()
                .map(product -> {

                    List<ImageDto> productImages = product.getProductImages().stream()
                            .map(ProductImage::getImage)
                            .sorted(Comparator.comparing(Image::getId))
                            .limit(1)
                            .map(image ->
                                    new ImageDto.Builder()
                                            .url(FileUtil.getImageUrl(ImageType.PRODUCT, product.getId(), image.getFileName()))
                                            .build())
                            .collect(Collectors.toList());

                    boolean isZzim = product.checkProductZzim(zzimProducts);
                    boolean isSeller = product.checkSeller(sellerProducts);

                    return new ProductDto.Builder()
                            .withId(product.getId())
                            .withName(product.getName())
                            .withBrand(BrandDto.fromBrand(product.getBrand()))
                            .withSize(product.getSize())
                            .withPrice(product.getPrice())
//                            .withDescription(product.getDescription())
                            .withStatus(product.getStatus())
//                            .withMainCategory(product.getCategory().getMain())
//                            .withSubCategory(product.getCategory().getSub())
//                            .withShop(shopDto)
                            .withImages(productImages)
                            .withZzimStatus(isZzim)
//                            .withCreatedAt(product.getCreatedTimestamp())
//                            .withUpdatedAt(product.getUpdatedTimestamp())
                            .withSeller(isSeller)
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(productPage.getNumber(), productPage.getSize());

        return new PageImpl<>(productDtos, resultPageable, productPage.getTotalElements());
    }
}
