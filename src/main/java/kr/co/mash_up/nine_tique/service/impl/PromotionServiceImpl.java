package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.domain.Image;
import kr.co.mash_up.nine_tique.domain.ImageType;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.Promotion;
import kr.co.mash_up.nine_tique.domain.PromotionImage;
import kr.co.mash_up.nine_tique.domain.PromotionProduct;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.dto.ImageDto;
import kr.co.mash_up.nine_tique.dto.PromotionDto;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.PromotionImageRepository;
import kr.co.mash_up.nine_tique.repository.PromotionRepository;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import kr.co.mash_up.nine_tique.service.PromotionService;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.ProductRequestVO;
import kr.co.mash_up.nine_tique.vo.PromotionRequestVO;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by ethankim on 2017. 7. 9..
 */
@Service
@Slf4j
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PromotionImageRepository promotionImageRepository;

    @Transactional
    @Override
    public void addPromotion(Long registerUserId, PromotionRequestVO promotionVO) {
        User register = userRepository.findOne(registerUserId);

        Promotion promotion = new Promotion();
        promotion.setName(promotionVO.getName());
        promotion.setDescription(promotionVO.getDescription());
        promotion.setPriority(promotionVO.getPriority());
        promotion.setStartAt(promotionVO.getStartAt().toLocalDateTime());
        promotion.setEndAt(promotionVO.getEndAt().toLocalDateTime());
        promotion.setRegister(register.getName());
        promotion.setPromotionImages(new ArrayList<>());
        promotion.setPromotionProducts(new ArrayList<>());

        promotionRepository.save(promotion);

        promotionVO.getProducts()
                .forEach(productVO -> {
                    Product product = productRepository.findOneByProductId(productVO.getId());
                    promotion.addProduct(new PromotionProduct(promotion, product));
                });

        // Todo: 이미지 entity refactoring 후 로직 수정
        // Todo: 문제점, 이미지가 저장되고 FK가 null로 남아있는다
        // 이미지 추가, 다른 request에서 저장된 파일을 FK로 연결시킨다
        // 현재 프로모션엔 대표 이미지 1개지만 확장성을 고려해 Collection으로 구현
        promotionVO.getImages()
                .forEach(imageDto -> {
                    Optional<PromotionImage> imageOptional = promotionImageRepository.findByFileName(FileUtil.getFileName(imageDto.getUrl()));
                    imageOptional.orElseThrow(() -> new IdNotFoundException("addPromotion -> image not found"));

                    PromotionImage promotionImage = imageOptional.get();
                    promotionImage.setPromotion(promotion);

                    // Todo: 이미지 request refactor 후 주석 제거
                    // file move tmp dir to product id dir
//                    FileUtil.moveFile(PromotionImage.getImageUploadTempPath() + "/" + promotionImage.getFileName(),
//                            promotionImage.getImageUploadPath());
                });
    }

    @Transactional
    @Override
    public void modifyPromotion(Long promotionId, PromotionRequestVO promotionVO) {
        Promotion promotion = promotionRepository.findOne(promotionId);
        Optional.ofNullable(promotion).orElseThrow(() -> new IdNotFoundException("modifyPromotion -> promotion not found"));

        promotion.setName(promotionVO.getName());
        promotion.setDescription(promotionVO.getDescription());
        promotion.setPriority(promotionVO.getPriority());
        promotion.setStartAt(promotionVO.getStartAt().toLocalDateTime());
        promotion.setEndAt(promotionVO.getEndAt().toLocalDateTime());

        List<PromotionProduct> oldProducts = promotion.getPromotionProducts();
        List<ProductRequestVO> updateProducts = promotionVO.getProducts();

        // 프로모션 상품 삭제
        Map<Long, ProductRequestVO> productMapToUpdate = updateProducts.stream()
                .collect(Collectors.toMap(ProductRequestVO::getId, productRequestVO -> productRequestVO));
        oldProducts.removeIf(promotionProduct -> productMapToUpdate.get(promotionProduct.getProductId()) == null);

        // 프로모션 상품 추가
        Map<Long, PromotionProduct> productMapToOld = oldProducts.stream()
                .collect(Collectors.toMap(PromotionProduct::getProductId, promotionProduct -> promotionProduct));
        updateProducts.forEach(productVO -> {
            if (productMapToOld.get(productVO.getId()) == null) {
                Product product = productRepository.findOneByProductId(productVO.getId());
                promotion.addProduct(new PromotionProduct(promotion, product));
            }
        });

        // Todo: 이미지 수정, 이미지 테이블 refactoring 후 구현한다

        promotionRepository.save(promotion);
    }

    @Transactional
    @Override
    public void removePromotion(Long promotionId) {
        Optional<Promotion> promotionOptional = promotionRepository.findOneByPromotionId(promotionId);
        promotionOptional.orElseThrow(() -> new IdNotFoundException("removePromotion -> promotion not found"));

        Promotion promotion = promotionOptional.get();
        promotion.deactive();
        promotionRepository.save(promotion);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PromotionDto> readPromotions(DataListRequestVO requestVO) {
        Pageable pageable = requestVO.getPageable();

        Page<Promotion> promotionPage = promotionRepository.findPromotions(pageable);

        List<PromotionDto> promotions = promotionPage.getContent().stream()
                .map(promotion -> {
                    List<PromotionImage> promotionImages = promotion.getPromotionImages();
                    List<ImageDto> images = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(promotionImages)) {
                        ImageDto image = new ImageDto.Builder()
                                .url(FileUtil.getImageUrl(ImageType.PROMOTION, promotion.getId(), promotionImages.get(0).getImage().getFileName()))
                                .build();
                        images.add(image);
                    }

                    return new PromotionDto.Builder()
                            .id(promotion.getId())
                            .name(promotion.getName())
                            .images(images)
                            .build();
                }).collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(promotionPage.getNumber(), promotionPage.getSize());

        return new PageImpl<>(promotions, resultPageable, promotionPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public PromotionDto readPromotion(Long promotionId) {
        Optional<Promotion> promotionOptional = promotionRepository.findOneByPromotionId(promotionId);
        promotionOptional.orElseThrow(() -> new IdNotFoundException("readPromotion -> promotion not found"));

        Promotion promotion = promotionOptional.get();

        List<ImageDto> images = new ArrayList<>();
        for (PromotionImage promotionImage : promotion.getPromotionImages()) {
            ImageDto image = new ImageDto.Builder()
                    .url(FileUtil.getImageUrl(ImageType.PROMOTION, promotion.getId(), promotionImage.getImage().getFileName()))
                    .build();
            images.add(image);
        }

        return new PromotionDto.Builder()
                .id(promotion.getId())
                .name(promotion.getName())
                .images(images)
                .build();
    }
}
