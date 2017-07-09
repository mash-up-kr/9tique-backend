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
import kr.co.mash_up.nine_tique.domain.ShopComment;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.dto.CommentDto;
import kr.co.mash_up.nine_tique.dto.ShopDto;
import kr.co.mash_up.nine_tique.exception.AlreadyExistException;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.exception.UserIdNotMatchedException;
import kr.co.mash_up.nine_tique.repository.ShopCommentRepository;
import kr.co.mash_up.nine_tique.repository.ShopRepository;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import kr.co.mash_up.nine_tique.service.ShopService;
import kr.co.mash_up.nine_tique.vo.CommentRequestVO;
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

    @Autowired
    private ShopCommentRepository shopCommentRepository;

    @Transactional
    @Override
    public void addShop(ShopRequestVO requestVO) {
        Optional<Shop> shop = shopRepository.findByNameAndPhoneNumber(requestVO.getName(), requestVO.getPhoneNumber());
        shop.orElseThrow(() -> new AlreadyExistException("addShop -> shop already exist"));
        shopRepository.save(requestVO.toShopEntitiy());
    }

    @Transactional
    @Override
    public void modifyShop(Long userId, Long shopId, ShopRequestVO requestVO) {
        Optional<Shop> oldShop = shopRepository.findOneByShopId(shopId);
        oldShop.orElseThrow(() -> new IdNotFoundException("modifyShop -> shop not found"));

        oldShop.ifPresent(shop -> {
            User user = userRepository.findOne(userId);

            // 판매자가 해당 shop에 속해있나
            if (!user.getSeller().matchShop(oldShop.get())) {
                throw new UserIdNotMatchedException("modifyShop -> forbbiden access");
            }

            shop.update(requestVO.toShopEntitiy());
            shopRepository.save(shop);
        });
    }

    @Transactional
    @Override
    public void removeShop(Long shopId) {
        Optional<Shop> oldShop = shopRepository.findOneByShopId(shopId);
        oldShop.orElseThrow(() -> new IdNotFoundException("removeShop -> shop not found"));

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
                .map(shop ->
                        new ShopDto.Builder()
                                .name(shop.getName())
                                .description(shop.getDescription())
                                .phoneNumber(shop.getPhoneNumber())
                                .kakaoOpenChatUrl(shop.getKakaoOpenChatUrl())
                                .build()
                ).collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(shopPage.getNumber(), shopPage.getSize());
        return new PageImpl<>(shopDtos, resultPageable, shopPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public ShopDto readShop(Long shopId) {
        Optional<Shop> shopOptional = shopRepository.findOneByShopId(shopId);
        shopOptional.orElseThrow(() -> new IdNotFoundException("readShop -> shop not found"));

        Shop shop = shopOptional.get();
        return new ShopDto.Builder()
                .name(shop.getName())
                .description(shop.getDescription())
                .phoneNumber(shop.getPhoneNumber())
                .kakaoOpenChatUrl(shop.getKakaoOpenChatUrl())
                .build();
    }

    @Transactional
    @Override
    public void addShopComment(Long shopId, Long userId, CommentRequestVO requestVO) {
        Optional<Shop> shopOptional = shopRepository.findOneByShopId(shopId);
        shopOptional.orElseThrow(() -> new IdNotFoundException("addShopComment -> shop not found"));

        User writer = userRepository.findOne(userId);

        ShopComment comment = new ShopComment();
        comment.setContents(requestVO.getContents());
        comment.setWriter(writer);
        comment.setShop(shopOptional.get());

        shopCommentRepository.save(comment);
    }

    @Transactional
    @Override
    public void modifyShopComment(Long shopId, Long commentId, Long userId, CommentRequestVO requestVO) {
        Optional<ShopComment> commentOptional = shopCommentRepository.findOneByShopIdAndCommentId(shopId, commentId);
        commentOptional.orElseThrow(() -> new IdNotFoundException("modifyShopCommentd -> comment not found"));

        ShopComment comment = commentOptional.get();

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
        commentOptional.orElseThrow(() -> new IdNotFoundException("modifyShopCommentd -> comment not found"));

        ShopComment comment = commentOptional.get();

        User user = userRepository.findOne(userId);
        if (!comment.matchWriter(user)) {
            throw new UserIdNotMatchedException("modifyShopComment -> forbbiden access");
        }

        comment.deactive();
        shopCommentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommentDto> readShopComments(Long shopId, DataListRequestVO requestVO) {
        Pageable pageable = requestVO.getPageable();

        Page<ShopComment> shopCommentPage = shopCommentRepository.findShopComments(shopId, pageable);

        List<CommentDto> shopComments = shopCommentPage.getContent().stream()
                .map(shopComment ->
                        new CommentDto.Builder()
                                .id(shopComment.getId())
                                .contents(shopComment.getContents())
                                .writerName(shopComment.getWriter().getName())
                                .build()
                ).collect(Collectors.toList());

        Pageable resultPageable = new PageRequest(shopCommentPage.getNumber(), shopCommentPage.getSize());
        return new PageImpl<>(shopComments, resultPageable, shopCommentPage.getTotalElements());
    }
}
