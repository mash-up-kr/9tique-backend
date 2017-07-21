package kr.co.mash_up.nine_tique.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Image;
import kr.co.mash_up.nine_tique.domain.ImageType;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.ProductImage;
import kr.co.mash_up.nine_tique.domain.Seller;
import kr.co.mash_up.nine_tique.domain.SellerProduct;
import kr.co.mash_up.nine_tique.domain.Shop;
import kr.co.mash_up.nine_tique.domain.ZzimProduct;
import kr.co.mash_up.nine_tique.dto.ImageDto;
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.dto.ShopDto;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.exception.UserIdNotMatchedException;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.repository.ImageRepository;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.SellerRepository;
import kr.co.mash_up.nine_tique.repository.ShopRepository;
import kr.co.mash_up.nine_tique.repository.ZzimRepository;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.ProductListRequestVO;
import kr.co.mash_up.nine_tique.vo.ProductRequestVO;
import lombok.extern.slf4j.Slf4j;

/**
 * Product와 관련된 비즈니스 로직 처리
 */
@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ZzimRepository zzimRepository;

    @Autowired
    private SellerRepository sellerRepository;

    /*
    ManyToOne은 알아서 join을 안한다.
    OneToMany는 알아서 join을 한다.
    트랜잭션안에서 1번이라도 쿼리를 날리면 join된다.
    -> fetch 전략때문임... -> LAZY와 EGEAR의 차이.
     */
    @Transactional(readOnly = true)
    public Page<ProductDto> findProductsByCategory(Long userId, ProductListRequestVO requestVO) {
        Pageable pageable = requestVO.getPageable();
        String mainCategory = requestVO.getMainCategory().toUpperCase();
        String subCategory = requestVO.getSubCategory().toUpperCase();

        Page<Product> productPage = null;

        if (mainCategory.equalsIgnoreCase("NEW")) {
            productPage = productRepository.findAll(pageable);
        } else {
            if (subCategory.equalsIgnoreCase("ALL")) {
                productPage = productRepository.findByMainCategory(pageable, mainCategory);
            } else {
                Category category = categoryRepository.findByMainAndSub(mainCategory, subCategory);
                Optional.ofNullable(category).orElseThrow(() -> new IdNotFoundException("find product by category -> category not found"));
                if (!category.isActive()) {
                    throw new IdNotFoundException("find product by category -> category not found");
                }

                log.debug(category.getMain() + " " + category.getSub() + " " + category.getId());

                productPage = productRepository.findByCategory(pageable, category);
            }
        }
        Optional.ofNullable(productPage).orElseThrow(() -> new IdNotFoundException("find product by category -> products not found"));

        List<ZzimProduct> zzimProducts = zzimRepository.getZzimProducts(userId);
        List<SellerProduct> sellerProducts = sellerRepository.getSellerProducts(userId);

        // DTO로 변환
        List<ProductDto> productDtos = productPage.getContent().stream()
                .map(product -> {

                    List<ImageDto> productImageDtos = product.getProductImages().stream()
                            .map(ProductImage::getImage)
                            .sorted(Comparator.comparingLong(Image::getId))
                            .map(image ->
                                    new ImageDto.Builder()
                                            .url(FileUtil.getImageUrl(ImageType.PRODUCT, product.getId(), image.getFileName()))
                                            .build()).collect(Collectors.toList());

                    ShopDto shopDto = new ShopDto.Builder()
                            .name(product.getShop().getName())
                            .description(product.getShop().getDescription())
                            .phoneNumber(product.getShop().getPhoneNumber())
                            .kakaoOpenChatUrl(product.getShop().getKakaoOpenChatUrl())
                            .build();

                    boolean isZzim = product.checkProductZzim(zzimProducts);
                    boolean isSeller = product.checkSeller(sellerProducts);

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
                            .withZzimStatus(isZzim)
                            .withCreatedAt(product.getCreatedTimestamp())
                            .withUpdatedAt(product.getUpdatedTimestamp())
                            .withSeller(isSeller)
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(productPage.getNumber(), productPage.getSize(),
                new Sort(Sort.Direction.DESC, "createdAt"));

        return new PageImpl<ProductDto>(productDtos, resultPageable, productPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public ProductDto findOne(Long userId, Long productid) {
        Product product = productRepository.findOne(productid);

        Optional.ofNullable(product).orElseThrow(() -> new IdNotFoundException("product find by id -> product not found"));

        List<ImageDto> productImageDtos = product.getProductImages().stream()
                .map(ProductImage::getImage)
                .sorted(Comparator.comparingLong(Image::getId))
                .map(productImage -> {
                    return new ImageDto.Builder()
                            .url(FileUtil.getImageUrl(ImageType.PRODUCT, product.getId(), productImage.getFileName()))
                            .build();
                }).collect(Collectors.toList());

        ShopDto shopDto = new ShopDto.Builder()
                .name(product.getShop().getName())
                .description(product.getShop().getDescription())
                .phoneNumber(product.getShop().getPhoneNumber())
                .kakaoOpenChatUrl(product.getShop().getKakaoOpenChatUrl())
                .build();

        List<ZzimProduct> zzimProducts = zzimRepository.getZzimProducts(userId);
        List<SellerProduct> sellerProducts = sellerRepository.getSellerProducts(userId);

        boolean isZzim = product.checkProductZzim(zzimProducts);
        boolean isSeller = product.checkSeller(sellerProducts);

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
                .withZzimStatus(isZzim)
                .withCreatedAt(product.getCreatedTimestamp())
                .withUpdatedAt(product.getUpdatedTimestamp())
                .withSeller(isSeller)
                .build();
    }

    @Transactional
    public Product update(Long userId, Long productId, ProductRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getName(), requestVO.getBrandName(), requestVO.getSize(),
                requestVO.getPrice(), requestVO.getDescription(), requestVO.getMainCategory(), requestVO.getImages());

        Product oldProduct = productRepository.findOne(productId);

        Optional.ofNullable(oldProduct).orElseThrow(() -> new IdNotFoundException("product update -> product not found"));
        if (!oldProduct.isActive()) {
            throw new IdNotFoundException("product update -> product not found");
        }

        Seller seller = sellerRepository.findByUserId(userId);
        Optional.ofNullable(seller).orElseThrow(() -> new IdNotFoundException("product update -> seller not found"));
        if (!seller.isActive()) {
            throw new IdNotFoundException("product update -> seller not found");
        }
        if (!oldProduct.matchShop(seller)) {  // 등록한 seller의 shop만 수정 가능
            throw new UserIdNotMatchedException("product update -> user id not matched");
        }

        Category category = categoryRepository.findByMainAndSub(requestVO.getMainCategory(), requestVO.getSubCategory());
        Optional.ofNullable(category).orElseThrow(() -> new IdNotFoundException("product update -> category not found"));
        if (!category.isActive()) {
            throw new IdNotFoundException("product update -> category not found");
        }

        List<ProductImage> oldImages = oldProduct.getProductImages();
        List<ImageDto> updateImages = requestVO.getImages();

        // 상품 이미지 삭제
        Map<String, ImageDto> imageMapToUpdate = updateImages.stream()
                .collect(Collectors.toMap(image -> FileUtil.getFileName(image.getUrl()), image -> image));

        Iterator<ProductImage> imageIterator = oldImages.iterator();
        while (imageIterator.hasNext()) {
            ProductImage oldImage = imageIterator.next();

            if (imageMapToUpdate.get(oldImage.getImage().getFileName()) == null) {
                // Todo: 2017.07.19 바로 파일 삭제할지 선택
                FileUtil.moveFile(FileUtil.getImageUploadPath(ImageType.PRODUCT, productId) + "/" + oldImage.getImage().getFileName()
                        , FileUtil.getImageUploadTempPath());
                imageIterator.remove();
            }
        }

        // 상품 이미지 추가
        updateImages.forEach(imageDto -> {
            Optional<Image> imageOptional = imageRepository.findByFileName(FileUtil.getFileName(imageDto.getUrl()));
            Image image = imageOptional.orElseThrow(() -> new IdNotFoundException("update -> image not found"));

            oldProduct.addImage(new ProductImage(oldProduct, image));

            // file move tmp dir to product id dir
            FileUtil.moveFile(FileUtil.getImageUploadTempPath() + "/" + image.getFileName(),
                    FileUtil.getImageUploadPath(ImageType.PRODUCT, productId));
        });

        // Todo: 없으면 어떻게 되는지 파악하고 제거
        requestVO.setStatus(oldProduct.getStatus().name());  // 이전상태 유지

        oldProduct.update(requestVO.toProductEntity(), category);
        return productRepository.save(oldProduct);
    }

    @Transactional
    public Product create(Long userId /* seller id */, ProductRequestVO requestVO) {
        Product product = requestVO.toProductEntity();

        //Todo: 상품이 이미 존재할 경우 예외처리. 상품을 뭐로 find할지 생각이 안난다..
        Shop shop = shopRepository.findByUserId(userId);
        Optional.ofNullable(shop).orElseThrow(() -> new IdNotFoundException("product create -> seller Infomation not found"));
//        if (!shop.isActive()) {
//            throw new IdNotFoundException("product create -> seller Infomation not found");
//        }
        product.setShop(shop);

        Category category = categoryRepository.findByMainAndSub(requestVO.getMainCategory(), requestVO.getSubCategory());
        Optional.ofNullable(category).orElseThrow(() -> new IdNotFoundException("product create -> category not found"));
        if (!category.isActive()) {
            throw new IdNotFoundException("product create -> category not found");
        }
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        // SellerProduct 저장
        Seller seller = sellerRepository.findByUserId(userId);
        SellerProduct sellerProduct = new SellerProduct(seller, savedProduct);
        seller.addSellerProduct(sellerProduct);

        requestVO.getImages().forEach(productImageDto -> {
            Optional<Image> imageOptional = imageRepository.findByFileName(FileUtil.getFileName(productImageDto.getUrl()));
            Image image = imageOptional.orElseThrow(() -> new IdNotFoundException("product create -> productImage not found"));

            savedProduct.addImage(new ProductImage(savedProduct, image));

            // file move tmp dir to product id dir
            FileUtil.moveFile(FileUtil.getImageUploadTempPath() + "/" + image.getFileName(),
                    FileUtil.getImageUploadPath(ImageType.PRODUCT, savedProduct.getId()));
        });

        return savedProduct;
    }

    @Transactional
    public void delete(Long userId, Long productId) {
        Product oldProduct = productRepository.findOneByProductId(productId);
        Optional.ofNullable(oldProduct).orElseThrow(() -> new IdNotFoundException("product delete -> product not found"));

        Seller seller = sellerRepository.findByUserId(userId);
        if (!oldProduct.matchShop(seller)) {  // 등록한 shop의 seller만 삭제 가능
            throw new UserIdNotMatchedException("product update -> user id not matched");
        }
        oldProduct.disable();

        // 이미지 디렉토리 삭제
//        FileUtil.deleteDir(oldProduct.getProductImages().get(0).getImageUploadPath());

        productRepository.save(oldProduct);
    }

    /**
     * 새로운 image list에 기존 데이터가 포함되어 있는지 확인
     *
     * @param fileName         확인할 기존 file name
     * @param productImageDtos 새로운 image list
     * @return true or false
     */
    private boolean existProductImageFromNewData(String fileName, List<ImageDto> productImageDtos) {
        for (ImageDto imageDto : productImageDtos) {
            log.info(FileUtil.getFileName(imageDto.getUrl()));
            if (fileName.equals(FileUtil.getFileName(imageDto.getUrl()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 기존 image list에 새로운 데이터가 포함되어 있는지 확인
     *
     * @param fileName      확인할 새로운 file name
     * @param productImages 기존 image list
     * @return true or false
     */
    private boolean existProductImageFromOldData(String fileName, List<ProductImage> productImages) {
        for (ProductImage productImage : productImages) {
//            log.info(productImage.getFileName());
//            if (fileName.equals(productImage.getFileName())) {
//                return true;
//            }
        }
        return false;
    }

    public Product updateStatus(Long userId, Long productId, ProductRequestVO requestVO) {
        Product oldProduct = productRepository.findOne(productId);

        Optional.ofNullable(oldProduct).orElseThrow(() -> new IdNotFoundException("product update -> product not found"));
        if (!oldProduct.isActive()) {
            throw new IdNotFoundException("product update -> product not found");
        }

        Seller seller = sellerRepository.findByUserId(userId);
        Optional.ofNullable(seller).orElseThrow(() -> new IdNotFoundException("product update -> seller not found"));
        if (!seller.isActive()) {
            throw new IdNotFoundException("product update -> seller not found");
        }
        if (!oldProduct.matchShop(seller)) {  // 등록한 seller의 shop만 수정 가능
            throw new UserIdNotMatchedException("product update -> user id not matched");
        }

        oldProduct.setStatus(requestVO.getStatus());
        return productRepository.save(oldProduct);
    }
}