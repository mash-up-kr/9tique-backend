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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.domain.Brand;
import kr.co.mash_up.nine_tique.domain.Category;
import kr.co.mash_up.nine_tique.domain.Image;
import kr.co.mash_up.nine_tique.domain.ImageType;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.ProductImage;
import kr.co.mash_up.nine_tique.domain.Seller;
import kr.co.mash_up.nine_tique.domain.SellerProduct;
import kr.co.mash_up.nine_tique.domain.ZzimProduct;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.exception.UserIdNotMatchedException;
import kr.co.mash_up.nine_tique.repository.BrandRepository;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.repository.ImageRepository;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.SellerRepository;
import kr.co.mash_up.nine_tique.repository.ShopRepository;
import kr.co.mash_up.nine_tique.repository.ZzimRepository;
import kr.co.mash_up.nine_tique.service.ProductService;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.web.dto.BrandDto;
import kr.co.mash_up.nine_tique.web.dto.CategoryDto;
import kr.co.mash_up.nine_tique.web.dto.ImageDto;
import kr.co.mash_up.nine_tique.web.dto.ProductDto;
import kr.co.mash_up.nine_tique.web.dto.ShopDto;
import kr.co.mash_up.nine_tique.web.vo.ProductListRequestVO;
import kr.co.mash_up.nine_tique.web.vo.ProductRequestVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

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

    @Autowired
    private BrandRepository brandRepository;

    @Transactional
    @Override
    public void addProduct(Long userId, ProductRequestVO requestVO) {
        Product product = requestVO.toProductEntity();
        //Todo: 상품이 이미 존재할 경우 여기서 예외처리. 상품을 뭐로 find할지 생각이 안난다..

        Optional<Seller> sellerOp = sellerRepository.findOneByUserId(userId);
        Seller seller = sellerOp.orElseThrow(() -> new IdNotFoundException("addProduct -> seller not found"));

        Optional<Category> categoryOp = categoryRepository.findOneByMainAndSub(requestVO.getMainCategory(), requestVO.getSubCategory());
        Category category = categoryOp.orElseThrow(() -> new IdNotFoundException("addProduct -> category not found"));

        Optional<Brand> brandOp = brandRepository.findByNameEng(requestVO.getBrandNameEng());
        Brand brand = brandOp.orElseThrow(() -> new IdNotFoundException("addProduct -> brand not found"));

        product.setShop(seller.getShop());
        product.setCategory(category);
        product.setBrand(brand);
        productRepository.save(product);

        // SellerProduct 저장
        SellerProduct sellerProduct = new SellerProduct(seller, product);
        seller.addSellerProduct(sellerProduct);

        requestVO.getImages().forEach(productImageDto -> {
            Optional<Image> imageOp = imageRepository.findByFileName(FileUtil.getFileName(productImageDto.getUrl()));
            Image image = imageOp.orElseThrow(() -> new IdNotFoundException("addProduct -> productImage not found"));

            product.addImage(new ProductImage(product, image));

            // file move tmp dir to product id dir
            FileUtil.moveFile(FileUtil.getImageUploadTempPath() + "/" + image.getFileName(),
                    FileUtil.getImageUploadPath(ImageType.PRODUCT, product.getId()));
        });
    }

    // Todo: 수정된 항목만 update해야 하는지...?
    @Transactional
    @Override
    public void modifyProduct(Long userId, Long productId, ProductRequestVO requestVO) {
        Optional<Product> productOp = productRepository.findOneByProductId(productId);
        Product product = productOp.orElseThrow(() -> new IdNotFoundException("modifyProduct -> product not found"));

        Optional<Seller> sellerOptional = sellerRepository.findOneByUserId(userId);
        Seller seller = sellerOptional.orElseThrow(() -> new IdNotFoundException("modifyProduct -> seller not found"));

        if (!product.matchShop(seller)) {  // 등록한 seller의 shop만 수정 가능
            throw new UserIdNotMatchedException("modifyProduct -> shop id not matched");
        }

        Optional<Category> categoryOp = categoryRepository.findOneByMainAndSub(requestVO.getMainCategory(), requestVO.getSubCategory());
        Category category = categoryOp.orElseThrow(() -> new IdNotFoundException("modifyProduct -> category not found"));

        Optional<Brand> brandOp = brandRepository.findByNameEng(requestVO.getBrandNameEng());
        Brand brand = brandOp.orElseThrow(() -> new IdNotFoundException("addProduct -> brand not found"));

        List<ProductImage> oldImages = product.getProductImages();
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
            Image image = imageOptional.orElseThrow(() -> new IdNotFoundException("modifyProduct -> image not found"));

            product.addImage(new ProductImage(product, image));

            // file move tmp dir to product id dir
            FileUtil.moveFile(FileUtil.getImageUploadTempPath() + "/" + image.getFileName(),
                    FileUtil.getImageUploadPath(ImageType.PRODUCT, productId));
        });

        // Todo: 없어도 이전 상태가 유지되는지? 어떻게 되는지 파악하고 제거
        requestVO.setStatus(product.getStatus());  // 이전상태 유지

        product.update(requestVO.toProductEntity(), category, brand);
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void modifyProductStatus(Long userId, Long productId, Product.Status newStatus) {
        Optional<Product> productOp = productRepository.findOneByProductId(productId);
        Product product = productOp.orElseThrow(() -> new IdNotFoundException("modifyProductStatus -> product not found"));

        Optional<Seller> sellerOptional = sellerRepository.findOneByUserId(userId);
        Seller seller = sellerOptional.orElseThrow(() -> new IdNotFoundException("modifyProductStatus -> seller not found"));

        if (!product.matchShop(seller)) {  // 등록한 seller의 shop만 수정 가능
            throw new UserIdNotMatchedException("modifyProductStatus -> shop id not matched");
        }

        if (!product.updateStatus(newStatus)) {
            throw new AlreadyExistException("modifyProductStatus -> already product status is modify");
        }

        productRepository.save(product);
    }

    @Transactional
    @Override
    public void removeProduct(Long userId, Long productId) {
        Optional<Product> productOp = productRepository.findOneByProductId(productId);
        Product product = productOp.orElseThrow(() -> new IdNotFoundException("removeProduct -> product not found"));

        Optional<Seller> sellerOptional = sellerRepository.findOneByUserId(userId);
        Seller seller = sellerOptional.orElseThrow(() -> new IdNotFoundException("removeProduct -> seller not found"));

        if (!product.matchShop(seller)) {  // 등록한 seller의 shop만 삭제 가능
            throw new UserIdNotMatchedException("removeProduct -> shop id not matched");
        }

        // 이미지 디렉토리 삭제
        FileUtil.moveFile(FileUtil.getImageUploadPath(ImageType.PRODUCT, productId), FileUtil.getImageUploadTempPath());
        productRepository.delete(productId);
    }

    /*
    ManyToOne은 알아서 join을 안한다.
    OneToMany는 알아서 join을 한다.
    트랜잭션안에서 1번이라도 쿼리를 날리면 join된다.
    -> fetch 전략때문임... -> LAZY와 EGEAR의 차이.

    NEW -> 모든 상품 최신순 정렬
    sub - ALL -> 상위 카테고리의 모든 하위 카테고리 상품 조회
    그외 sub 카테고리의 상품 리스트 조회
     */
    @Transactional(readOnly = true)
    @Override
    public Page<ProductDto> readProductsByCategory(Long userId, ProductListRequestVO requestVO) {
        Pageable pageable = requestVO.getPageable();
        String mainCategory = requestVO.getMainCategory().toUpperCase();
        String subCategory = requestVO.getSubCategory().toUpperCase();

        Page<Product> productPage;

        if (mainCategory.equalsIgnoreCase("NEW")) {
            productPage = productRepository.findProducts(pageable);
        } else {
            if (subCategory.equalsIgnoreCase("ALL")) {
                productPage = productRepository.findProductsByMainCategory(mainCategory, pageable);
            } else {
                Optional<Category> categoryOptional = categoryRepository.findOneByMainAndSub(mainCategory, subCategory);
                Category category = categoryOptional.orElseThrow(() -> new IdNotFoundException("find product by category -> category not found"));

                log.debug(category.getMain() + " " + category.getSub() + " " + category.getId());

                productPage = productRepository.findProductsByCategory(category, pageable);
            }
        }

        List<ZzimProduct> zzimProducts = zzimRepository.findZzimProducts(userId);
        List<SellerProduct> sellerProducts = sellerRepository.findSellerProducts(userId);

        List<ProductDto> productDtos = productPage.getContent().stream()
                .map(product -> {
                    // Todo: 상품 리스트에선 대표 이미지 1개만 필요 전부다 내릴 필요가 있을까?
                    List<ImageDto> productImages = product.getProductImages().stream()
                            .map(ProductImage::getImage)
                            .sorted(Comparator.comparingLong(Image::getId))
                            .map(image ->
                                    new ImageDto.Builder()
                                            .url(FileUtil.getImageUrl(ImageType.PRODUCT, product.getId(), image.getFileName()))
                                            .build()).collect(Collectors.toList());

                    // Todo: 필요 없는 정보 주석 처리
//                    Shop shop = product.getShop();
//                    ShopDto shopDto = new ShopDto.Builder()
//                            .name(shop.getName())
//                            .description(shop.getDescription())
//                            .phoneNumber(shop.getPhoneNumber())
//                            .kakaoOpenChatUrl(shop.getKakaoOpenChatUrl())
//                            .build();

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

        Pageable resultPageable = new PageRequest(productPage.getNumber(), productPage.getSize(),
                new Sort(Sort.Direction.DESC, "createdAt"));

        return new PageImpl<>(productDtos, resultPageable, productPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDto readProduct(Long userId, Long productId) {
        Optional<Product> productOptional = productRepository.findOneByProductId(productId);
        Product product = productOptional.orElseThrow(() -> new IdNotFoundException("readProduct -> product not found"));

        List<ImageDto> productImageDtos = product.getProductImages().stream()
                .map(ProductImage::getImage)
                .sorted(Comparator.comparingLong(Image::getId))
                .map(productImage ->
                        new ImageDto.Builder()
                                .url(FileUtil.getImageUrl(ImageType.PRODUCT, product.getId(), productImage.getFileName()))
                                .build()
                ).collect(Collectors.toList());

        ShopDto shopDto = new ShopDto.Builder()
                .name(product.getShop().getName())
                .description(product.getShop().getDescription())
                .phoneNumber(product.getShop().getPhoneNumber())
                .kakaoOpenChatUrl(product.getShop().getKakaoOpenChatUrl())
                .build();

        // Todo: 들고와서 체크할 필요가 있을까?
        List<ZzimProduct> zzimProducts = zzimRepository.findZzimProducts(userId);
        List<SellerProduct> sellerProducts = sellerRepository.findSellerProducts(userId);

        boolean isZzim = product.checkProductZzim(zzimProducts);
        boolean isSeller = product.checkSeller(sellerProducts);

        return new ProductDto.Builder()
                .withId(product.getId())
                .withName(product.getName())
                .withBrand(BrandDto.fromBrand(product.getBrand()))
                .withSize(product.getSize())
                .withPrice(product.getPrice())
                .withDescription(product.getDescription())
                .withStatus(product.getStatus())
                .withCategory(CategoryDto.fromCategory(product.getCategory()))
                .withShop(shopDto)
                .withImages(productImageDtos)
                .withZzimStatus(isZzim)
                .withSeller(isSeller)
                .build();
    }
}
