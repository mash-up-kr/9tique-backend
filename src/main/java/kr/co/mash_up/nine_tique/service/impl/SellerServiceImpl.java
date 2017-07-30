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

import kr.co.mash_up.nine_tique.domain.Authority;
import kr.co.mash_up.nine_tique.domain.Image;
import kr.co.mash_up.nine_tique.domain.ImageType;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.ProductImage;
import kr.co.mash_up.nine_tique.domain.Seller;
import kr.co.mash_up.nine_tique.domain.SellerProduct;
import kr.co.mash_up.nine_tique.domain.Shop;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.web.dto.ImageDto;
import kr.co.mash_up.nine_tique.web.dto.ProductDto;
import kr.co.mash_up.nine_tique.web.dto.SellerDto;
import kr.co.mash_up.nine_tique.web.dto.ShopDto;
import kr.co.mash_up.nine_tique.web.dto.UserDto;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.exception.UserIdNotMatchedException;
import kr.co.mash_up.nine_tique.repository.AuthorityRepository;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.SellerRepository;
import kr.co.mash_up.nine_tique.repository.ShopRepository;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import kr.co.mash_up.nine_tique.security.Authorities;
import kr.co.mash_up.nine_tique.security.JwtTokenUtil;
import kr.co.mash_up.nine_tique.service.SellerService;
import kr.co.mash_up.nine_tique.util.CodeGeneratorUtil;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.web.vo.ProductDeleteRequestVO;
import kr.co.mash_up.nine_tique.web.vo.ProductRequestVO;
import kr.co.mash_up.nine_tique.web.vo.SellerRequestVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopRepository shopRepository;

    // Todo: 상품 리스트 조회 기능 공통되면 분리하기
    @Transactional(readOnly = true)
    @Override
    public Page<ProductDto> readSellerProducts(Long userId, Pageable pageable) {
        Page<SellerProduct> sellerProductPage = sellerRepository.findSellerProducts(userId, pageable);

        List<ProductDto> productDtos = sellerProductPage.getContent().stream()
                .map(SellerProduct::getProduct)
                .map(product -> {
                    List<ImageDto> productImages = product.getProductImages().stream()
                            .map(ProductImage::getImage)
                            .sorted(Comparator.comparingLong(Image::getId))
                            .map(productImage ->
                                 new ImageDto.Builder()
                                        .url(FileUtil.getImageUrl(ImageType.PRODUCT, product.getId(), productImage.getFileName()))
                                        .build()).collect(Collectors.toList());

                    // Todo: 필요 없는 정보 주석 처리
//                    ShopDto shopDto = new ShopDto.Builder()
//                            .name(product.getShop().getName())
//                            .description(product.getShop().getDescription())
//                            .phoneNumber(product.getShop().getPhoneNumber())
//                            .build();

                    return new ProductDto.Builder()
                            .withId(product.getId())
                            .withName(product.getName())

                            // Todo: 브랜드쪽 수정, 한글만 내려갈지, 한글+영어로 내려갈지
                            .withBrandName(product.getBrand().getNameKo())
                            .withSize(product.getSize())
                            .withPrice(product.getPrice())
//                            .withDescription(product.getDescription())
                            .withStatus(product.getStatus())
//                            .withCategory(CategoryDto.fromCategory(product.getCategory()))
//                            .withShop(shopDto)
                            .withImages(productImages)
                            /*
                             현재 요구사항으로 판매자는 찜기능이 없다.
                             요구사항의 변경으로 필요할지도 모르니 주석처리
                                .withZzimStatus(isZzim)
                              */
//                            .withCreatedAt(product.getCreatedTimestamp())
//                            .withUpdatedAt(product.getUpdatedTimestamp())
                            .withSeller(true)  // 판매자가 등록한 상품 목록이므로 true
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(sellerProductPage.getNumber(), sellerProductPage.getSize(),
                new Sort(Sort.Direction.DESC, "createdAt"));

        return new PageImpl<>(productDtos, resultPageable, sellerProductPage.getTotalElements());
    }

    @Transactional
    @Override
    public void removeProductsAll(Long userId) {
        List<SellerProduct> sellerProducts = sellerRepository.findSellerProducts(userId);

        sellerProducts.stream()
                .map(sellerProduct -> sellerProduct.getId().getProductId())
                .forEach(productId -> {
                    // dir move tmp dir to product id dir
                    FileUtil.moveFile(FileUtil.getImageUploadPath(ImageType.PRODUCT, productId), FileUtil.getImageUploadTempPath());
                    productRepository.delete(productId);
                });
    }

    @Transactional
    @Override
    public void removeProducts(Long userId, ProductDeleteRequestVO requestVO) {
        Optional<Seller> sellerOp = sellerRepository.findOneByUserId(userId);
        Seller seller = sellerOp.orElseThrow(() -> new IdNotFoundException("removeProduct -> seller not found"));

        requestVO.getProducts().stream()
                .map(ProductRequestVO::getId)
                .forEach(productId -> {
                    Optional<Product> productOp = productRepository.findOneByProductId(productId);
                    Product product = productOp.orElseThrow(() -> new IdNotFoundException("removeProduct -> product not found"));

                    // 판매자가 등록한 상품이 맞는지 검증
                    if (!product.matchShop(seller)) {
                        throw new UserIdNotMatchedException("product delete -> user id not matched");
                    }

                    // dir move tmp dir to product id dir
                    FileUtil.moveFile(FileUtil.getImageUploadPath(ImageType.PRODUCT, productId), FileUtil.getImageUploadTempPath());
                    productRepository.delete(productId);
                });
    }

    @Transactional(readOnly = true)
    @Override
    public SellerDto readSellerInfo(Long userId) {
        Optional<Seller> sellerOp = sellerRepository.findOneByUserId(userId);
        Seller seller = sellerOp.orElseThrow(() -> new IdNotFoundException("find seller by user id -> seller not found"));

        User user = seller.getUser();
        UserDto userDto = new UserDto.Builder()
                .name(user.getName())
                .build();

        Shop shop = seller.getShop();
        ShopDto shopDto = new ShopDto.Builder()
                .name(shop.getName())
                .description(shop.getDescription())
                .phoneNumber(shop.getPhoneNumber())
                .build();

        return new SellerDto.Builder()
                .user(userDto)
                .shop(shopDto)
                .build();
    }

    @Transactional
    @Override
    public UserDto registerSeller(Long userId, String authentiCode) {
        // 존재하지 않는 인증 코드
        Optional<Seller> sellerOp = sellerRepository.findByAuthentiCode(authentiCode);
        Seller seller = sellerOp.orElseThrow(() -> new IdNotFoundException("registerSeller -> invalid authenticode"));

        if (seller.alreadyRegistration()) {
            throw new AlreadyExistException("registerSeller -> already registered authentiCode");
        }

        Optional<User> userOp = userRepository.findOneById(userId);
        User user = userOp.orElseThrow(() -> new IdNotFoundException("registerSeller -> user not found"));

        if (user.alreadySeller()) {
            throw new AlreadyExistException("registerSeller -> already registered user");
        }

        // Seller - User 연결
        seller.registerSeller(user);
        sellerRepository.save(seller);

        // Seller 권한 저장
        Authority authority = authorityRepository.findByAuthority(Authorities.SELLER);
        user.addAuthority(authority);
        userRepository.save(user);

        return new UserDto.Builder()
                .accessToken(jwtTokenUtil.generateToken(user))
                .authorityLevel(user.findAuthority())
                .build();
    }

    @Transactional
    @Override
    public void addSeller(Long shopId) {
        Optional<Shop> shopOp = shopRepository.findOneByShopId(shopId);
        Shop shop = shopOp.orElseThrow(() -> new IdNotFoundException("addSeller -> shop not found"));

        Seller seller = new Seller(shop, CodeGeneratorUtil.generateAuthentiCode());
        sellerRepository.save(seller);
    }

    @Transactional
    @Override
    public void modifySeller(Long userId, SellerRequestVO requestVO) {
        Optional<Seller> sellerOp = sellerRepository.findOneByUserId(userId);
        Seller seller = sellerOp.orElseThrow(() -> new IdNotFoundException("modifySeller -> seller not found"));

        seller.updateShop(requestVO.toShopEntity());
        seller.updateName(requestVO.getSellerName());
        sellerRepository.save(seller);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SellerDto> readSellers(Pageable pageable) {
        Page<Seller> sellerPage = sellerRepository.findAll(pageable);

        List<SellerDto> sellerDtos = sellerPage.getContent().stream()
                .map(seller -> {
                    User user = seller.getUser();
                    UserDto userDto = new UserDto.Builder()
                            .name(user.getName())
                            .build();

                    Shop shop = seller.getShop();
                    ShopDto shopDto = new ShopDto.Builder()
                            .id(shop.getId())
                            .name(shop.getName())
                            .description(shop.getDescription())
                            .phoneNumber(shop.getPhoneNumber())
                            .build();

                    return new SellerDto.Builder()
                            .authentiCode(seller.getAuthentiCode())
                            .user(userDto)
                            .shop(shopDto)
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(sellerPage.getNumber(), sellerPage.getSize(),
                new Sort(Sort.Direction.DESC, "id"));

        return new PageImpl<>(sellerDtos, resultPageable, sellerPage.getTotalElements());
    }
}
