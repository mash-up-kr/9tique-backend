package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.domain.Promotion;
import kr.co.mash_up.nine_tique.domain.PromotionImage;
import kr.co.mash_up.nine_tique.domain.PromotionProduct;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.dto.PromotionDto;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.ProductRepository;
import kr.co.mash_up.nine_tique.repository.PromotionImageRepository;
import kr.co.mash_up.nine_tique.repository.PromotionRepository;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import kr.co.mash_up.nine_tique.service.PromotionService;
import kr.co.mash_up.nine_tique.util.FileUtil;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
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

        // 이미지 추가, 다른 request에서 저장된 파일을 FK로 연결시킨다
        // 현재 프로모션엔 대표 이미지 1개지만 확장성을 고려해 Collection으로 구현
        promotionVO.getImages()
                .forEach(imageDto -> {

                    log.info("{}", PromotionImage.getFileNameFromUrl(imageDto.getUrl()));

                    Optional<PromotionImage> imageOptional = promotionImageRepository.findByFileName(PromotionImage.getFileNameFromUrl(imageDto.getUrl()));
                    imageOptional.orElseThrow(() -> new IdNotFoundException("addPromotion -> image not found"));

                    PromotionImage promotionImage = imageOptional.get();
                    promotionImage.setPromotion(promotion);

                    // file move tmp dir to product id dir
//                    FileUtil.moveFile(PromotionImage.getImageUploadTempPath() + "/" + promotionImage.getFileName(),
//                            promotionImage.getImageUploadPath());
                });
    }

    @Transactional
    @Override
    public void modifyPromotion(Long promotionId, PromotionRequestVO promotionVO) {
        // Todo: 현재 프로모션엔 대표 이미지 1개다
    }

    @Transactional
    @Override
    public void removePromotion(Long promotionId) {

    }

    @Transactional(readOnly = true)
    @Override
    public Page<PromotionDto> readPromotions(DataListRequestVO requestVO) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public PromotionDto readPromotion(Long promotionId) {
        return null;
    }
}
