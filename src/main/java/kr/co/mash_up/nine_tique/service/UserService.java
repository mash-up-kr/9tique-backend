package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.*;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.*;
import kr.co.mash_up.nine_tique.security.Authorities;
import kr.co.mash_up.nine_tique.security.JwtTokenUtil;
import kr.co.mash_up.nine_tique.vo.UserRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User와 관련된 비즈니스 로직 처리
 */
@Service(value = "userService")
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ZzimRepository zzimRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public void findOne(Long id) {
        userRepository.findOne(id);
    }

    @Transactional
    public String login(UserRequestVO requestVO) {
        User user = userRepository.findByOauthTokenAndOauthType(requestVO.getOauthToken(), requestVO.getType());

        if (user == null) {  // User가 없으면 정보 저장
            user = requestVO.toUserEntity();  // oauth token, oauth type setting

            // USER 권한 저장
            Authority authority = authorityRepository.findByAuthority(Authorities.USER);
            user.getAuthorities().add(authority);

            userRepository.save(user);

            // zzim 연관관계 생성
            Zzim zzim = new Zzim();
            zzim.setUser(user);
            zzimRepository.save(zzim);
        }

        return jwtTokenUtil.generateToken(user);
    }

    /**
     * 판매자 권한 추가
     * Access Token으로 user정보를 찾아 SELLER 권한을 추가한다.
     *
     * @param id user를 찾을 id
     */
    @Transactional
    public String addSellerAuthority(Long id) {
        User user = userRepository.findOne(id);

        if (user == null) {
            throw new IdNotFoundException("user not found");
        }

        //Todo: 인증코드 검증
        // Seller Infomation 저장
        // Todo: Seller Infomation 저장된거랑 맞춘다.
        Shop shop = new Shop();
        shop.setName("매장 name" + id);
        shop.setInfo("매장 info" + id);
        shop.setPhone("010-3222-2222" + id);
        shopRepository.save(shop);

        Seller seller = new Seller(shop, user);
        sellerRepository.save(seller);

        // Seller 권한 저장
        Authority authority = authorityRepository.findByAuthority(Authorities.SELLER);
        user.getAuthorities().add(authority);
        userRepository.save(user);

        return jwtTokenUtil.generateToken(user);
    }
}
