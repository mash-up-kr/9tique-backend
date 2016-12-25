package kr.co.mash_up.nine_tique.service;

import kr.co.mash_up.nine_tique.domain.Authority;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.domain.Zzim;
import kr.co.mash_up.nine_tique.repository.AuthorityRepository;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import kr.co.mash_up.nine_tique.repository.ZzimRepository;
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
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ZzimRepository zzimRepository;

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

        if (user != null) {
            // SELLER 권한 저장
            Authority authority = authorityRepository.findByAuthority(Authorities.SELLER);
            user.getAuthorities().add(authority);

            userRepository.save(user);

            return jwtTokenUtil.generateToken(user);
        }
        //Todo: 실패 응답 어떻게 할지 정의
        return "false";
    }
}
