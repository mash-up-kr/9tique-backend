package kr.co.mash_up.nine_tique.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.mash_up.nine_tique.domain.Authority;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.repository.AuthorityRepository;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import kr.co.mash_up.nine_tique.security.Authorities;
import kr.co.mash_up.nine_tique.vo.UserRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;


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

    //Todo: key 숨기기
    String secretKey = "qTLY/BYBom546U8mvYwdE/59JbYY+qKucaEme8Z8jQbyF5MvXuWNnkJOmTSguaWbB9R00hpoI/DUdZF2zee26A";

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
        }

        return generateAccessToken(user);
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

            return generateAccessToken(user);
        }
        //Todo: 실패 응답 어떻게 할지 정의
        return "false";
    }



    //jwt 생성해서 리턴
    private String generateAccessToken(User user) {
        String authority = Authorities.USER;

        for (GrantedAuthority grantedAuthority : user.getAuthoritiesWithoutPersistence()) {
            String tmp = grantedAuthority.getAuthority();
            if (tmp.equals(Authorities.ADMIN)) {
                authority = Authorities.ADMIN;
                break;
            } else if (tmp.equals(Authorities.SELLER)) {
                authority = Authorities.SELLER;
                break;
            }
        }

        //Todo: 만료시간 정하기
        return Jwts.builder()
                .setSubject("access_token")  // token 제목
                .setIssuer("nine_tique")  // token 발급자
                .setIssuedAt(new Date())  // 발급시간
//                .setExpiration(new Date())  // 만료시간
                // public 클레임
                .claim("http://mash-up.co.kr/9tique/jwt_claims", true)
                // private 클레임
                .claim("user_id", user.getId().toString())
                .claim("oauth_token", user.getOauthToken())
                .claim("oauth_type", user.getOauthType())
                .claim("roles", authority)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
