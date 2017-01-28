package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.*;
import kr.co.mash_up.nine_tique.dto.ProductDto;
import kr.co.mash_up.nine_tique.dto.ProductImageDto;
import kr.co.mash_up.nine_tique.dto.ShopDto;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.exception.UserIdNotMatchedException;
import kr.co.mash_up.nine_tique.repository.*;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.ProductListRequestVO;
import kr.co.mash_up.nine_tique.vo.ProductRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Product와 관련된 비즈니스 로직 처리
 */
@Service(value = "productService")
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ZzimRepository zzimRepository;

    @Autowired
    private UserRepository userRepository;

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

        if (mainCategory.equals("NEW")) {
            productPage = productRepository.findAll(pageable);
        } else {
            if (subCategory.equals("ALL")) {
                productPage = productRepository.findByMainCategory(pageable, mainCategory);
            } else {
                Category category = categoryRepository.findByMainAndSubAllIgnoreCase(mainCategory, subCategory);
                if (category == null) {
                    throw new IdNotFoundException("find product by category -> category not found");
                }
                log.debug(category.getMain() + " " + category.getSub() + " " + category.getId());
                productPage = productRepository.findByCategory(pageable, category);
            }
        }

        if (productPage == null) {
            throw new IdNotFoundException("find product by category -> products not found");
        }

        List<ZzimProduct> zzimProducts = zzimRepository.getZzimProducts(userId);
        List<SellerProduct> sellerProducts = sellerRepository.getSellerProducts(userId);

        // DTO로 변환
        List<ProductDto> productDtos = productPage.getContent().stream()
                .map(product -> {
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

                    boolean isZzim = checkProductZzim(zzimProducts, product);
                    boolean isSeller = checkSeller(sellerProducts, product);

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

        if (product == null) {
            throw new IdNotFoundException("product find by id -> product not found");
        }

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

        List<ZzimProduct> zzimProducts = zzimRepository.getZzimProducts(userId);
        boolean isZzim = checkProductZzim(zzimProducts, product);

        List<SellerProduct> sellerProducts = sellerRepository.getSellerProducts(userId);
        boolean isSeller = checkSeller(sellerProducts, product);

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
                .withZzimStatus(isZzim)
                .withCreatedAt(product.getCreatedTimestamp())
                .withUpdatedAt(product.getUpdatedTimestamp())
                .withSeller(isSeller)
                .build();
    }

    @Transactional
    public Product update(Long userId, Long productId, ProductRequestVO requestVO) {
        Product oldProduct = productRepository.findOne(productId);

        if (oldProduct == null) {
            throw new IdNotFoundException("product update -> product not found");
        }

        User user = userRepository.findOne(userId);
        if (!Objects.equals(user.getSeller().getShop().getId(), oldProduct.getShop().getId())) {  // 등록한 shop의 seller만 삭제 가능
            throw new UserIdNotMatchedException("product update -> user id not matched");
        }

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

        //Todo: shop이 update되어야하는지...?
        // product의 shop이 바뀔 수 없을거 같은데...?
        Shop shop = shopRepository.findByUserId(userId);
        if (shop == null) {
            throw new IdNotFoundException("product update -> seller infomation not found");
        }
        oldProduct.setShop(shop);

        String mainCategory = requestVO.getMainCategory();
        String subCategory = requestVO.getSubCategory();  // ""가 있어서 체크하지 않는다.
        if (!ParameterUtil.isEmpty(mainCategory)) {
            Category category = categoryRepository.findByMainAndSubAllIgnoreCase(mainCategory, subCategory);
            if (category == null) {
                throw new IdNotFoundException("product update -> category not found");
            }
            oldProduct.setCategory(category);
        }

        String productStatus = requestVO.getStatus();
        if (productStatus != null && productStatus.equals("SELL")) {
            oldProduct.setStatus(Product.Status.SELL);
        } else if (productStatus != null && productStatus.equals("SOLD_OUT")) {
            oldProduct.setStatus(Product.Status.SOLD_OUT);
        }

        // image url이 왔다는건 이미지 수정이 있다는 것
        List<ProductImageDto> productImageDtos = requestVO.getProductImages();
        if (!ParameterUtil.isEmpty(productImageDtos)) {
            /*
            경우의 수
            1. 사진 추가(카메라) 1장
            사진등록 + 참조 연결
             등록한 사진 url이 더해져서 온다.

            2. 사진 추가(갤러리) 여러장 - 새로 등록하는 과정
                1. 기존 사진 참조해제
                2. 사진 등록 + 참조연결

            3. 사진 제거 - 일정한 갯수의 사진이 사라졌으니 확인 해야함
            제거된 사진 확인 + 참조 해제

            4. 제거 + 추가
            제거된 사진 확인 + 참조 해제

            쨌든 제거되고 등록된다고 햇을 때

	        수정에서 날리는건 url인데
            클라에서

            이미지에 1번 다녀오니까
            url을 +, - 해서 최종 url list가 만들어지면 request
            *4번 경우만 아니면 상당히 쉬워지는 문제
            음... 카메라 기능을 없애자.

            사진 제거면 url이 사라질테니 문제X
            제거 + 추가면
            어떤거는 multipart로 만들고 어떤거는 url을 보내야 한다.
            */

            // 새로운 Product Image가 기존 Product Image가 없으면 기존 Product Image 참조 해제
            List<ProductImage> oldProductImages = oldProduct.getProductImages();
            oldProductImages.forEach(productImage -> {
                String fileName = productImage.getFileName();

                if (!existProductImageFromNewData(fileName, productImageDtos)) {  // 포함되어 있지 않으므로 tmp로 옮기고 참조를 끊는다.

                    // file move product/{id} dir to tmp dir
                    FileUtil.moveFile(productImage.getImageUploadPath() + "/" + fileName, productImage.getImageUploadTempPath());
                    productImage.setProduct(null);
                }
            });

            // 기존 Product Image에 새로운 Product Image가 없으면 참조 연결 -> tmp dir에 파일 존재
            productImageDtos.forEach(productImageDto -> {
                String fileName = ProductImage.getFileNameFromUrl(productImageDto.getUrl());

                if (!existProductImageFromOldData(fileName, oldProductImages)) {
                    ProductImage productImage = productImageRepository.findByFileName(fileName);
                    productImage.setProduct(oldProduct);

                    // file move tmp dir to product/{id} dir
                    FileUtil.moveFile(productImage.getImageUploadTempPath() + "/" + productImage.getFileName(),
                            productImage.getImageUploadPath());
                }
            });
        }

        return productRepository.save(oldProduct);
    }

    @Transactional
    public Product create(Long userId /* seller id */, ProductRequestVO requestVO) {
        Product product = requestVO.toProductEntity();

        //Todo: 상품이 이미 존재할 경우 예외처리. 상품을 뭐로 find할지 생각이 안난다..

        Shop shop = shopRepository.findByUserId(userId);
        if (shop == null) {
            throw new IdNotFoundException("product create -> seller Infomation not found");
        }
        product.setShop(shop);

        Category category = categoryRepository.findByMainAndSubAllIgnoreCase(requestVO.getMainCategory(), requestVO.getSubCategory());
        if (category == null) {
            throw new IdNotFoundException("product create -> category not found");
        }
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        // SellerProduct 저장
        Seller seller = sellerRepository.findByUserId(userId);
        SellerProduct sellerProduct = new SellerProduct(seller, savedProduct);
        seller.getSellerProducts().add(sellerProduct);

        requestVO.getProductImages().forEach(productImageDto -> {
            ProductImage productImage
                    = productImageRepository.findByFileName(ProductImage.getFileNameFromUrl(productImageDto.getUrl()));
            productImage.setProduct(product);

            // file move tmp dir to product id dir
            FileUtil.moveFile(productImage.getImageUploadTempPath() + "/" + productImage.getFileName(),
                    productImage.getImageUploadPath());
        });

        return savedProduct;
    }

    @Transactional
    public void delete(Long userId, Long productId) {
        Product oldProduct = productRepository.findOne(productId);
        if (oldProduct == null) {
            throw new IdNotFoundException("product delete -> product not found");
        }

        User user = userRepository.findOne(userId);
        if (!Objects.equals(user.getSeller().getShop().getId(), oldProduct.getShop().getId())) {  // 등록한 shop의 seller만 삭제 가능
            throw new UserIdNotMatchedException("product update -> user id not matched");
        }

        // 이미지 디렉토리 삭제
        FileUtil.deleteDir(oldProduct.getProductImages().get(0).getImageUploadPath());

        productRepository.delete(productId);
    }

    /**
     * 상품이 찜 되었는지 확인
     *
     * @param zzimProducts 유저의 찜한 상품 목록
     * @param product      확인할 상품
     * @return 결과
     */
    private boolean checkProductZzim(List<ZzimProduct> zzimProducts, Product product) {
        for (ZzimProduct zzimProduct : zzimProducts) {
            if (Objects.equals(zzimProduct.getProduct().getId(), product.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 상품이 내가 등록한 상품인지 확인
     *
     * @param sellerProducts 판매자가 등록한 상품 목록
     * @param product        확인할 상품
     * @return 내가 등록한 상품인지 여부
     */
    private boolean checkSeller(List<SellerProduct> sellerProducts, Product product) {
        for (SellerProduct sellerProduct : sellerProducts) {
            if (Objects.equals(sellerProduct.getProduct().getId(), product.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 새로운 image list에 기존 데이터가 포함되어 있는지 확인
     *
     * @param fileName         확인할 기존 file name
     * @param productImageDtos 새로운 image list
     * @return true or false
     */
    private boolean existProductImageFromNewData(String fileName, List<ProductImageDto> productImageDtos) {
        for (ProductImageDto productImageDto : productImageDtos) {
            log.info(ProductImage.getFileNameFromUrl(productImageDto.getUrl()));
            if (fileName.equals(ProductImage.getFileNameFromUrl(productImageDto.getUrl()))) {
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
            log.info(productImage.getFileName());
            if (fileName.equals(productImage.getFileName())) {
                return true;
            }
        }
        return false;
    }
}