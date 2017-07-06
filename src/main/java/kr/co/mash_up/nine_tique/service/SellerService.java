package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.*;
import kr.co.mash_up.nine_tique.dto.*;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.exception.UserIdNotMatchedException;
import kr.co.mash_up.nine_tique.repository.*;
import kr.co.mash_up.nine_tique.security.Authorities;
import kr.co.mash_up.nine_tique.security.JwtTokenUtil;
import kr.co.mash_up.nine_tique.util.CodeGeneratorUtil;
import kr.co.mash_up.nine_tique.vo.ProductDeleteRequestVO;
import kr.co.mash_up.nine_tique.vo.ProductRequestVO;
import kr.co.mash_up.nine_tique.vo.SellerRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Seller와 관련된 비즈니스 로직 처리
 */
@Service(value = "sellerService")
@Slf4j
public class SellerService {

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

    /**
     * 판매자가 등록한 상품 리스트 조회
     *
     * @param userId   seller id
     * @param pageable page 정보
     * @return 판매자가 등록한 상품 리스트
     */
    @Transactional(readOnly = true)
    public Page<ProductDto> findProducts(Long userId, Pageable pageable) {
        Page<SellerProduct> sellerProductPage = sellerRepository.getSellerProducts(userId, pageable);

        Optional.ofNullable(sellerProductPage).orElseThrow(() -> new IdNotFoundException("selle products not found"));

        List<ProductDto> productDtos = sellerProductPage.getContent().stream()
                .map(sellerProduct -> {
                    Product product = sellerProduct.getProduct();

                    List<ProductImageDto> productImageDtos = product.getProductImages().stream()
                            .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                            .map(productImage -> {
                                return new ProductImageDto.Builder()
                                        .withUrl(productImage.getImageUrl())
                                        .build();
                            }).collect(Collectors.toList());

                    ShopDto shopDto = new ShopDto.Builder()
                            .withName(product.getShop().getName())
                            .withInfo(product.getShop().getDescription())
                            .withPhone(product.getShop().getPhoneNumber())
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
                            .withProductImages(productImageDtos)
                            /*
                             현재 요구사항으로 판매자는 찜기능이 없다.
                             요구사항의 변경으로 필요할지도 모르니 주석처리
                                .withZzimStatus(isZzim)
                              */
                            .withCreatedAt(product.getCreatedTimestamp())
                            .withUpdatedAt(product.getUpdatedTimestamp())
                            .withSeller(true)  // 판매자가 등록한 상품 목록이므로 true
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(sellerProductPage.getNumber(), sellerProductPage.getSize(),
                new Sort(Sort.Direction.DESC, "createdAt"));

        return new PageImpl<ProductDto>(productDtos, resultPageable, sellerProductPage.getTotalElements());
    }

    /**
     * 판매자가 등록한 상품 전체삭제
     *
     * @param userId Seller id
     */
    @Transactional
    public void deleteProductsAll(Long userId) {
        List<SellerProduct> sellerProducts = sellerRepository.getSellerProducts(userId);

        Optional.ofNullable(sellerProducts).orElseThrow(() -> new IdNotFoundException("sell product delete all -> product not found"));

        sellerProducts.forEach(sellerProduct -> {
            Product oldProduct = sellerProduct.getProduct();
            oldProduct.disable();

            // 이미지 디렉토리 삭제
//            FileUtil.deleteDir(oldProduct.getProductImages().get(0).getImageUploadPath());

            productRepository.save(oldProduct);
        });
    }

    /**
     * 판매자가 등록한 상품 삭제
     *
     * @param userId    Seller id
     * @param requestVO Product id
     */
    @Transactional
    public void deleteProducts(Long userId, ProductDeleteRequestVO requestVO) {
        requestVO.getProducts().stream()
                .map(ProductRequestVO::getId)
                .forEach(productId -> {
                    Product oldProduct = productRepository.findOne(productId);
                    productRepository.findOneById(productId);
                    Optional.ofNullable(oldProduct).orElseThrow(() -> new IdNotFoundException("product delete -> product not found"));

                    Seller seller = sellerRepository.findByUserId(userId);
                    if (!oldProduct.matchShop(seller)) {
                        throw new UserIdNotMatchedException("product delete -> user id not matched");
                    }

                    // 이미지 디렉토리 삭제
//                    FileUtil.deleteDir(oldProduct.getProductImages().get(0).getImageUploadPath());
                    oldProduct.disable();
                    productRepository.save(oldProduct);
                });
    }

    /**
     * seller의 shop & name 조회
     *
     * @param userId 조회할 유저 id
     * @return 조회한 정보
     */
    @Transactional(readOnly = true)
    public SellerDto findSellerByUserId(Long userId) {
        Seller seller = sellerRepository.findByUserId(userId);
        Optional.ofNullable(seller).orElseThrow(() -> new IdNotFoundException("find seller by user id -> seller not found"));

        User user = seller.getUser();
        UserDto userDto = new UserDto.Builder()
                .withName(user.getName())
                .build();

        Shop shop = seller.getShop();
        ShopDto shopDto = new ShopDto.Builder()
                .withName(shop.getName())
                .withInfo(shop.getDescription())
                .withPhone(shop.getPhoneNumber())
                .build();

        return new SellerDto.Builder()
                .withUserDto(userDto)
                .withShopDto(shopDto)
                .build();
    }

    /**
     * 판매자 인증 및 권한 추가
     * 인증코드로 seller를 찾아 유저와 연결
     *
     * @param userId       유저 id
     * @param authentiCode 인증코드
     * @return 생성된 access token
     */
    @Transactional
    public UserDto sellerAuthenticateAndAddAuthority(Long userId, String authentiCode) {
        Seller seller = sellerRepository.findByAuthentiCode(authentiCode);
        Optional.ofNullable(seller).orElseThrow(() -> new IdNotFoundException("register seller -> seller not found, invalid authenti code"));
        if (seller.isActive()) {
            throw new AlreadyExistException("register seller -> already register seller");
        }

        User user = userRepository.findOne(userId);
        Optional.ofNullable(user).orElseThrow(() -> new IdNotFoundException("register seller -> user not found"));

        // Seller - User 연결
        seller.registerSeller(user);
        sellerRepository.save(seller);

        // Seller 권한 저장
        Authority authority = authorityRepository.findByAuthority(Authorities.SELLER);
        user.addAuthority(authority);
        userRepository.save(user);

        return new UserDto.Builder()
                .withAccessToken(jwtTokenUtil.generateToken(user))
                .withAuthorityLevel(user.findAuthority())
                .build();
    }

    /**
     * 판매자 생성 -> 매장 연결, 인증코드 발급
     *
     * @param shopId 연결할 매장 id
     * @return 생성된 판매자
     */
    @Transactional
    public Seller create(Long shopId) {
        Shop shop = shopRepository.findOne(shopId);
        Optional.ofNullable(shop).orElseThrow(() -> new IdNotFoundException("seller create -> shop not found"));
        if (!shop.isActive()) {
            throw new IdNotFoundException("seller create -> shop not found");
        }

        Seller seller = new Seller(shop, CodeGeneratorUtil.generateAuthentiCode());
        return sellerRepository.save(seller);
    }

    /**
     * 판매자 정보 수정
     *
     * @param userId    seller를 얻어올 유저 id
     * @param requestVO 수정할 판매자 정보(shop, user name)
     * @return 수정된 판매자
     */
    @Transactional
    public Seller update(Long userId, SellerRequestVO requestVO) {
        Seller seller = sellerRepository.findByUserId(userId);
        Optional.ofNullable(seller).orElseThrow(() -> new IdNotFoundException("seller update -> seller not found"));
        if (!seller.isActive()) {
            throw new IdNotFoundException("seller update -> seller not found");
        }

        seller.getShop().update(requestVO.toShopEntity());
        seller.getUser().updateName(requestVO.getSellerName());

        return sellerRepository.save(seller);
    }

    public Page<SellerDto> findSellers(Pageable pageable) {
        Page<Seller> sellerPage = sellerRepository.findAll(pageable);

        List<SellerDto> sellerDtos = sellerPage.getContent().stream()
                .map(seller -> {
                    User user = seller.getUser();
                    UserDto userDto = new UserDto.Builder()
                            .withName(user.getName())
                            .build();

                    Shop shop = seller.getShop();
                    ShopDto shopDto = new ShopDto.Builder()
                            .withId(shop.getId())
                            .withName(shop.getName())
                            .withInfo(shop.getDescription())
                            .withPhone(shop.getPhoneNumber())
                            .build();

                    return new SellerDto.Builder()
                            .withAuthentiCode(seller.getAuthentiCode())
                            .withUserDto(userDto)
                            .withShopDto(shopDto)
                            .build();
                })
                .collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(sellerPage.getNumber(), sellerPage.getSize(),
                new Sort(Sort.Direction.DESC, "createdAt"));

        return new PageImpl<SellerDto>(sellerDtos, resultPageable, sellerPage.getTotalElements());
    }
}