package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.domain.*;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.exception.UserIdNotMatchedException;
import kr.co.mash_up.nine_tique.repository.CategoryRepository;
import kr.co.mash_up.nine_tique.repository.ImageRepository;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.SellerRepository;
import kr.co.mash_up.nine_tique.repository.ShopCommentRepository;
import kr.co.mash_up.nine_tique.repository.ShopRepository;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import kr.co.mash_up.nine_tique.repository.ZzimRepository;
import kr.co.mash_up.nine_tique.service.ShopService;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.web.dto.BrandDto;
import kr.co.mash_up.nine_tique.web.dto.CommentDto;
import kr.co.mash_up.nine_tique.web.dto.ImageDto;
import kr.co.mash_up.nine_tique.web.dto.ProductDto;
import kr.co.mash_up.nine_tique.web.dto.ShopDto;
import kr.co.mash_up.nine_tique.web.dto.UserDto;
import kr.co.mash_up.nine_tique.web.vo.CommentRequestVO;
import kr.co.mash_up.nine_tique.web.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.web.vo.ProductListRequestVO;
import kr.co.mash_up.nine_tique.web.vo.ShopRequestVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopCommentRepository shopCommentRepository;

    @Autowired
    private ImageRepository imageRepository;

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
    public void addShop(ShopRequestVO requestVO) {
        Optional<Shop> shopOptional = shopRepository.findByNameAndPhoneNumber(requestVO.getName(), requestVO.getPhoneNumber());
        shopOptional.ifPresent(shop -> {
            throw new AlreadyExistException("addShop -> shop already exist");
        });

        Shop shop = requestVO.toShopEntitiy();
        shop.setCommentCount(0L);
        shopRepository.save(shop);

        // 현재 매장엔 대표 이미지 1개지만 확장성을 고려해 Collection으로 구현
        requestVO.getImages()
                .forEach(imageDto -> {
                    Optional<Image> imageOptional = imageRepository.findByFileName(FileUtil.getFileName(imageDto.getUrl()));
                    Image image = imageOptional.orElseThrow(() -> new IdNotFoundException("addShop -> image not found"));

                    shop.addImage(new ShopImage(shop, image));

                    // file move tmp dir to promotion id dir
                    FileUtil.moveFile(FileUtil.getImageUploadTempPath() + "/" + image.getFileName(),
                            FileUtil.getImageUploadPath(ImageType.SHOP, shop.getId()));
                });
    }

    @Transactional
    @Override
    public void modifyShop(Long userId, Long shopId, ShopRequestVO requestVO) {
        Optional<Shop> shopOptional = shopRepository.findOneByShopId(shopId);
        Shop oldShop = shopOptional.orElseThrow(() -> new IdNotFoundException("modifyShop -> shop not found"));

        // 판매자가 해당 shop에 속해있나
        User user = userRepository.findOne(userId);
        if (!user.getSeller().matchShop(oldShop)) {
            throw new UserIdNotMatchedException("modifyShop -> forbbiden access");
        }

        List<ShopImage> oldImages = oldShop.getShopImages();
        List<ImageDto> updateImages = requestVO.getImages();

        // 매장 이미지 삭제
        Map<String, ImageDto> imageMapToUpdate = updateImages.stream()
                .collect(Collectors.toMap(image -> FileUtil.getFileName(image.getUrl()), image -> image));

        Iterator<ShopImage> imageIterator = oldImages.iterator();
        while (imageIterator.hasNext()) {
            ShopImage oldImage = imageIterator.next();

            if (imageMapToUpdate.get(oldImage.getImage().getFileName()) == null) {
                // Todo: 2017.07.19 바로 파일 삭제할지 선택
                FileUtil.moveFile(FileUtil.getImageUploadPath(ImageType.SHOP, shopId) + "/" + oldImage.getImage().getFileName()
                        , FileUtil.getImageUploadTempPath());
                imageIterator.remove();
            }
        }

        // 매장 이미지 추가
        updateImages.forEach(imageDto -> {
            Optional<Image> imageOptional = imageRepository.findByFileName(FileUtil.getFileName(imageDto.getUrl()));
            Image image = imageOptional.orElseThrow(() -> new IdNotFoundException("modifyShop -> image not found"));

            oldShop.addImage(new ShopImage(oldShop, image));

            // file move tmp dir to promotion id dir
            FileUtil.moveFile(FileUtil.getImageUploadTempPath() + "/" + image.getFileName(),
                    FileUtil.getImageUploadPath(ImageType.SHOP, shopId));
        });

        oldShop.update(requestVO.toShopEntitiy());
        shopRepository.save(oldShop);
    }

    @Transactional
    @Override
    public void removeShop(Long shopId) {
        Optional<Shop> shopOptional = shopRepository.findOneByShopId(shopId);
        Shop oldShop = shopOptional.orElseThrow(() -> new IdNotFoundException("removeShop -> shop not found"));

        // dir move tmp dir to shop id dir
        FileUtil.moveFile(FileUtil.getImageUploadPath(ImageType.SHOP, shopId), FileUtil.getImageUploadTempPath());
        shopRepository.delete(oldShop);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ShopDto> readShops(DataListRequestVO requestVO) {
        Pageable pageable = requestVO.getPageable();

        Page<Shop> shopPage = shopRepository.findShops(pageable);

        List<ShopDto> shopDtos = shopPage.getContent().stream()
                .map(shop -> {
                    List<ImageDto> images = shop.getShopImages().stream()
                            .map(ShopImage::getImage)
                            .map(image ->
                                    new ImageDto.Builder()
                                            .url(FileUtil.getImageUrl(ImageType.SHOP, shop.getId(), image.getFileName()))
                                            .build())
                            .collect(Collectors.toList());

                    return new ShopDto.Builder()
                            .name(shop.getName())
                            .description(shop.getDescription())
                            .phoneNumber(shop.getPhoneNumber())
                            .kakaoOpenChatUrl(shop.getKakaoOpenChatUrl())
                            .images(images)
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(shopPage.getNumber(), shopPage.getSize());
        return new PageImpl<>(shopDtos, resultPageable, shopPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public ShopDto readShop(Long shopId) {
        Optional<Shop> shopOptional = shopRepository.findOneByShopId(shopId);
        Shop shop = shopOptional.orElseThrow(() -> new IdNotFoundException("readShop -> shop not found"));

        List<ImageDto> images = shop.getShopImages().stream()
                .map(ShopImage::getImage)
                .map(image ->
                        new ImageDto.Builder()
                                .url(FileUtil.getImageUrl(ImageType.SHOP, shopId, image.getFileName()))
                                .build())
                .collect(Collectors.toList());

        return new ShopDto.Builder()
                .name(shop.getName())
                .description(shop.getDescription())
                .phoneNumber(shop.getPhoneNumber())
                .kakaoOpenChatUrl(shop.getKakaoOpenChatUrl())
                .images(images)
                .build();
    }

    @Transactional
    @Override
    public void addShopComment(Long shopId, Long userId, CommentRequestVO requestVO) {
        Optional<Shop> shopOptional = shopRepository.findOneByShopId(shopId);
        Shop shop = shopOptional.orElseThrow(() -> new IdNotFoundException("addShopComment -> shop not found"));

        User writer = userRepository.findOne(userId);

        ShopComment comment = new ShopComment(shop, writer, requestVO.getContents());
        shop.addComment(comment);

        shopRepository.save(shop);
    }

    @Transactional
    @Override
    public void modifyShopComment(Long shopId, Long commentId, Long userId, CommentRequestVO requestVO) {
        Optional<ShopComment> commentOptional = shopCommentRepository.findOneByShopIdAndCommentId(shopId, commentId);
        ShopComment comment = commentOptional.orElseThrow(() -> new IdNotFoundException("modifyShopComment -> comment not found"));

        User user = userRepository.findOne(userId);
        if (!comment.matchWriter(user)) {
            throw new UserIdNotMatchedException("modifyShopComment -> forbbiden access");
        }

        comment.update(requestVO.getContents());
        shopCommentRepository.save(comment);
    }

    @Transactional
    @Override
    public void removeShopComment(Long shopId, Long commentId, Long userId) {
        Optional<ShopComment> commentOptional = shopCommentRepository.findOneByShopIdAndCommentId(shopId, commentId);
        ShopComment comment = commentOptional.orElseThrow(() -> new IdNotFoundException("removeShopComment -> comment not found"));

        User user = userRepository.findOne(userId);
        if (!comment.matchWriter(user)) {
            throw new UserIdNotMatchedException("removeShopComment -> forbbiden access");
        }

        Shop shop = comment.getShop();
        shop.removeComment();

        shopCommentRepository.delete(comment);
        shopRepository.save(shop);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommentDto> readShopComments(Long shopId, DataListRequestVO requestVO) {
        Pageable pageable = requestVO.getPageable();

        Page<ShopComment> shopCommentPage = shopCommentRepository.findShopComments(shopId, pageable);

        List<CommentDto> shopComments = shopCommentPage.getContent().stream()
                .map(shopComment -> {
                    User writer = shopComment.getWriter();

                    UserDto userDto = new UserDto.Builder()
                            .name(writer.getName())
                            .profileImageUrl(writer.getProfileImageUrl())
                            .build();

                    return new CommentDto.Builder()
                            .id(shopComment.getId())
                            .contents(shopComment.getContents())
                            .writer(userDto)
                            .updatedAt(shopComment.getUpdatedTimestamp())
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(shopCommentPage.getNumber(), shopCommentPage.getSize());
        return new PageImpl<>(shopComments, resultPageable, shopCommentPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductDto> readShopProducts(Long shopId, Long userId, ProductListRequestVO requestVO) {
        Pageable pageable = requestVO.getPageable();
        String mainCategory = requestVO.getMainCategory().toUpperCase();
        String subCategory = requestVO.getSubCategory().toUpperCase();

        Page<Product> productPage;

        if (mainCategory.equalsIgnoreCase("NEW")) {
            productPage = productRepository.findShopProducts(shopId, pageable);
        } else {
            if (subCategory.equalsIgnoreCase("ALL")) {
                productPage = productRepository.findShopProductsByMainCategory(shopId, mainCategory, pageable);
            } else {
                Optional<Category> categoryOptional = categoryRepository.findOneByMainAndSub(mainCategory, subCategory);
                Category category = categoryOptional.orElseThrow(() -> new IdNotFoundException("find product by category -> category not found"));

                log.debug(category.getMain() + " " + category.getSub() + " " + category.getId());

                productPage = productRepository.findShopProductsByCategory(shopId, category, pageable);
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
