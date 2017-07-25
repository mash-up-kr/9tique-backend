package kr.co.mash_up.nine_tique.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import kr.co.mash_up.nine_tique.domain.Authority;
import kr.co.mash_up.nine_tique.domain.User;
import kr.co.mash_up.nine_tique.domain.Zzim;
import kr.co.mash_up.nine_tique.dto.UserDto;
import kr.co.mash_up.nine_tique.exception.IdNotFoundException;
import kr.co.mash_up.nine_tique.repository.AuthorityRepository;
import kr.co.mash_up.nine_tique.repository.UserRepository;
import kr.co.mash_up.nine_tique.repository.ZzimRepository;
import kr.co.mash_up.nine_tique.security.Authorities;
import kr.co.mash_up.nine_tique.security.JwtTokenUtil;
import kr.co.mash_up.nine_tique.vo.UserRequestVO;
import lombok.extern.slf4j.Slf4j;

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
    public UserDto login(UserRequestVO requestVO) {
//        User oldUser = userRepository.findByOauthTokenAndOauthType(requestVO.getOauthToken(), requestVO.getType());
        User oldUser = userRepository.findByEmail(requestVO.getEmail());

        oldUser = Optional.ofNullable(oldUser).orElseGet(() -> {  // User가 없으면 정보 저장
            User newUser = requestVO.toUserEntity();

            // USER 권한 저장
            Authority authority = authorityRepository.findByAuthority(Authorities.USER);
            newUser.addAuthority(authority);
            userRepository.save(newUser);

            // default zzim 연관관계 생성
            Zzim zzim = new Zzim(newUser);
            zzimRepository.save(zzim);

            return newUser;
        });

        return new UserDto.Builder()
                .accessToken(jwtTokenUtil.generateToken(oldUser))
                .authorityLevel(oldUser.findAuthority())
                .build();
    }

    /**
     * 관리자 권한 추가
     * ????로 admin 권한 등록
     *
     * @param userId 유저 id
     * @return 생성된 access token
     */
    public UserDto addAdminAuthority(Long userId) {
        User user = userRepository.findOne(userId);
        Optional.ofNullable(user).orElseThrow(() -> new IdNotFoundException("register admin -> user not found"));

        Authority authority = authorityRepository.findByAuthority(Authorities.ADMIN);
        user.addAuthority(authority);
        userRepository.save(user);

        return new UserDto.Builder()
                .accessToken(jwtTokenUtil.generateToken(user))
                .authorityLevel(user.findAuthority())
                .build();
    }

    public static final String HEADER_PREFIX = "Bearer ";

    public UserDto tokenRefresh(String authHeader) {
        String accessToken = authHeader.substring(HEADER_PREFIX.length());
        String newAccessToken = jwtTokenUtil.refreshToken(accessToken);
        return new UserDto.Builder()
                .accessToken(newAccessToken)
                .build();
    }
}
