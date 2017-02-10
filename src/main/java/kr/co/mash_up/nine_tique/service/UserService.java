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

import java.util.Optional;

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
        User oldUser = userRepository.findByOauthTokenAndOauthType(requestVO.getOauthToken(), requestVO.getType());

        oldUser = Optional.ofNullable(oldUser).orElseGet(() -> {  // User가 없으면 정보 저장
            User newUser = requestVO.toUserEntity();  // oauth token, oauth type setting

            // USER 권한 저장
            Authority authority = authorityRepository.findByAuthority(Authorities.USER);
            newUser.getAuthorities().add(authority);

            userRepository.save(newUser);

            // default zzim 연관관계 생성
            Zzim zzim = new Zzim();
            zzim.setUser(newUser);
            zzimRepository.save(zzim);

            return newUser;
        });

        return jwtTokenUtil.generateToken(oldUser);
    }

    /**
     * 판매자 권한 추가
     * 인증코드로 shop정보를 찾아 seller로 등록한다
     *
     * @param userId       유저 id
     * @param authentiCode 인증코드
     * @return 생성된 access token
     */
    @Transactional
    public String addSellerAuthority(Long userId, String authentiCode) {
        User user = userRepository.findOne(userId);
        Optional.ofNullable(user).orElseThrow(() -> new IdNotFoundException("register seller -> user not found"));

        Shop shop = shopRepository.findByAuthentiCode(authentiCode);
        Optional.ofNullable(shop).orElseThrow(() -> new IdNotFoundException("register seller -> shop not found, invalid authenti code"));
        if (!shop.isEnabled()) {
            throw new IdNotFoundException("register seller -> shop not found, invalid authenti code");
        }

        // Seller 저장
        Seller seller = new Seller(shop, user);
        sellerRepository.save(seller);

        // Seller 권한 저장
        Authority authority = authorityRepository.findByAuthority(Authorities.SELLER);
        user.getAuthorities().add(authority);
        userRepository.save(user);

        return jwtTokenUtil.generateToken(user);
    }

    /**
     * 관리자 권한 추가
     * ????로 admin 권한 등록
     *
     * @param userId 유저 id
     * @return 생성된 access token
     */
    public String addAdminAuthority(Long userId) {
        User user = userRepository.findOne(userId);
        Optional.ofNullable(user).orElseThrow(() -> new IdNotFoundException("register admin -> user not found"));

        Authority authority = authorityRepository.findByAuthority(Authorities.ADMIN);
        user.getAuthorities().add(authority);
        userRepository.save(user);

        return jwtTokenUtil.generateToken(user);
    }
}