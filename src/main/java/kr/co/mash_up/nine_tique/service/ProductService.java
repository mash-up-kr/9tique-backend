package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.ProductImage;
import kr.co.mash_up.nine_tique.domain.SellerInfo;
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.dto.ProductImageDto;
import kr.co.mash_up.nine_tique.dto.SellerInfoDto;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.repository.ProductImageRepository;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.SellerInfoRepository;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.ProductListRequestVO;
import kr.co.mash_up.nine_tique.vo.ProductRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *  Product와 관련된 비즈니스 로직 처리
 */
@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    /*
    ManyToOne은 알아서 join을 안한다.
    OneToMany는 알아서 join을 한다.
    트랜잭션안에서 1번이라도 쿼리를 날리면 join된다.
    -> fetch 전략때문임... -> LAZY와 EGEAR의 차이.
     */
    @Transactional(readOnly = true)
    public Page<ProductDto> findProductsByCategory(ProductListRequestVO requestVO) {
        Category category = null;

        Pageable pageable = requestVO.getPageable();
        String mainCategory = requestVO.getMainCategory();
        String subCategory = requestVO.getSubCategory();

        if (mainCategory != null && subCategory != null) {
            category = categoryRepository.findByMainAndSubAllIgnoreCase(mainCategory, subCategory);
            log.debug(category.getMain() + " " + category.getSub() + " " + category.getId());
        }

        Page<Product> productPage = productRepository.findByCategory(pageable, category);

        // DTO로 변환
        List<ProductDto> productDtos = productPage.getContent().stream()
                .map(product -> {
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

        Pageable resultPageable = new PageRequest(productPage.getNumber(), productPage.getSize(),
                new Sort(Sort.Direction.DESC, "createdAt"));

        return new PageImpl<ProductDto>(productDtos, resultPageable, productPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public ProductDto findOne(Long id) {
        Product product = productRepository.findOne(id);

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
    }

    @Transactional
    public Product update(Long id, ProductRequestVO requestVO) {
        Product oldProduct = productRepository.findOne(id);

        if (oldProduct != null) {

            // 바뀐 정보만 update
            String name = requestVO.getName();
            if (!ParameterUtil.isEmpty(name)) {
                oldProduct.setName(name);
            }

            String brandName = requestVO.getBrandName();
            if (!ParameterUtil.isEmpty(brandName)) {
                oldProduct.setBrandName(brandName);
            }

            String size = requestVO.getSize();
            if (!ParameterUtil.isEmpty(size)) {
                oldProduct.setSize(size);
            }

            int price = requestVO.getPrice();
            if (!ParameterUtil.isEmpty(price)) {
                oldProduct.setPrice(price);
            }

            String description = requestVO.getDescription();
            if (!ParameterUtil.isEmpty(description)) {
                oldProduct.setDescription(description);
            }

            long sellerId = requestVO.getSellerId();
            if (!ParameterUtil.isEmpty(sellerId)) {
                SellerInfo sellerInfo = sellerInfoRepository.findOne(sellerId);
                oldProduct.setSellerInfo(sellerInfo);
            }

            String mainCategory = requestVO.getMainCategory();
            String subCategory = requestVO.getSubCategory();  // ""가 있어서 체크하지 않는다.
            if (!ParameterUtil.isEmpty(mainCategory)) {
                Category category = categoryRepository.findByMainAndSubAllIgnoreCase(mainCategory, subCategory);
                oldProduct.setCategory(category);
            }

            String productStatus = requestVO.getProductStatus();
            if (productStatus.equals("SELL")) {
                oldProduct.setStatus(Product.Status.SELL);
            } else if (productStatus.equals("SOLD_OUT")) {
                oldProduct.setStatus(Product.Status.SOLD_OUT);
            }

            List<MultipartFile> files = requestVO.getFiles();
            if (!ParameterUtil.isEmpty(files)) {
                saveMultipartFile(files, oldProduct);
            }
        }

        return productRepository.save(oldProduct);
    }

    @Transactional
    public Product save(ProductRequestVO requestVO) {
        Product product = requestVO.toProductEntity();

        SellerInfo sellerInfo = sellerInfoRepository.findOne(requestVO.getSellerId());
        product.setSellerInfo(sellerInfo);

        Category category = categoryRepository.findByMainAndSubAllIgnoreCase(requestVO.getMainCategory(), requestVO.getSubCategory());
        product.setCategory(category);

        if (requestVO.getProductStatus().equals("SELL")) {
            product.setStatus(Product.Status.SELL);
        } else if (requestVO.getProductStatus().equals("SOLD_OUT")) {
            product.setStatus(Product.Status.SOLD_OUT);
        }

        Product savedProduct = productRepository.save(product);

        List<MultipartFile> files = requestVO.getFiles();
        saveMultipartFile(files, savedProduct);

        return savedProduct;
    }

    @Transactional
    public void delete(Long id) {
        productRepository.delete(id);
    }

    private void saveMultipartFile(List<MultipartFile> files, Product product) {

        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                ProductImage productImage = new ProductImage();

                // 고유이름 생성
                String saveName = UUID.randomUUID().toString() +
                        file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                log.debug(saveName);

                productImage.setFileName(saveName);
                productImage.setOriginalFileName(file.getOriginalFilename());
                productImage.setSize(file.getSize());
                productImage.setProduct(product);

                FileUtil.upload(file, productImage.getImageUploadPath(), saveName);  // 저장

//                FileUtil.upload(file, FileUtil.getImageUploadPath(savedProduct.getId()), saveName);
//                productImage.setImageUrl(FileUtil.getImageUrl(savedProduct.getId(), saveName));

                productImageRepository.save(productImage);
                log.debug(file.getOriginalFilename());
            }
        }
    }
}