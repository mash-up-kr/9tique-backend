package kr.co.mash_up.nine_tique.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.co.mash_up.nine_tique.domain.Shop;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.dto.ShopDto;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.exception.UserIdNotMatchedException;
import kr.co.mash_up.nine_tique.repository.ShopRepository;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import kr.co.mash_up.nine_tique.service.ShopService;
import kr.co.mash_up.nine_tique.vo.DataListRequestVO;
import kr.co.mash_up.nine_tique.vo.ShopRequestVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void addShop(ShopRequestVO requestVO) {
        Optional<Shop> shop = shopRepository.findByNameAndPhoneNumber(requestVO.getName(), requestVO.getPhoneNumber());
        shop.orElseThrow(() -> new AlreadyExistException("shop add -> shop already exist"));
        shopRepository.save(requestVO.toShopEntitiy());
    }

    @Transactional
    @Override
    public void modifyShop(Long userId, Long shopId, ShopRequestVO requestVO) {
        Optional<Shop> oldShop = shopRepository.findOneByShopId(shopId);
        oldShop.orElseThrow(() -> new IdNotFoundException("shop modify -> shop not found"));

        oldShop.ifPresent(shop -> {
            User user = userRepository.findOne(userId);

            // 판매자가 해당 shop에 속해있나
            if (!user.getSeller().matchShop(oldShop.get())) {
                throw new UserIdNotMatchedException("shop update -> forbbiden access");
            }

            shop.update(requestVO.toShopEntitiy());
            shopRepository.save(shop);
        });
    }

    @Transactional
    @Override
    public void removeShop(Long shopId) {
        Optional<Shop> oldShop = shopRepository.findOneByShopId(shopId);
        oldShop.orElseThrow(() -> new IdNotFoundException("shop remove -> shop not found"));

        oldShop.ifPresent(shop -> {
            shop.deactive();
            shopRepository.save(shop);
        });
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ShopDto> readShops(DataListRequestVO requestVO) {
        Pageable pageable = requestVO.getPageable();

        Page<Shop> shopPage = shopRepository.findShops(pageable);

        List<ShopDto> shopDtos = shopPage.getContent().stream()
                .map(shop -> new ShopDto.Builder()
                        .withName(shop.getName())
                        .withInfo(shop.getDescription())
                        .withPhone(shop.getPhoneNumber())
                        .withKakaoOpenChatUrl(shop.getKakaoOpenChatUrl())
                        .build()
                ).collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(shopPage.getNumber(), shopPage.getSize());
        return new PageImpl<ShopDto>(shopDtos, resultPageable, shopPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public ShopDto readShop(Long shopId) {
        Optional<Shop> shopOptional = shopRepository.findOneByShopId(shopId);
        shopOptional.orElseThrow(() -> new IdNotFoundException("shop find by id -> shop not found"));

        Shop shop = shopOptional.get();
        return new ShopDto.Builder()
                .withName(shop.getName())
                .withInfo(shop.getDescription())
                .withPhone(shop.getPhoneNumber())
                .withKakaoOpenChatUrl(shop.getKakaoOpenChatUrl())
                .build();
    }
}
