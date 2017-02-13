package kr.co.mash_up.nine_tique.controller;

import kr.co.mash_up.nine_tique.security.Authorities;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.UserService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.UserRequestVO;
import kr.co.mash_up.nine_tique.vo.UserResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_USER;

@RestController
@RequestMapping(value = API_USER)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public UserResponseVO login(@RequestBody UserRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getOauthToken(), requestVO.getType());

        // return access token
        String token = userService.login(requestVO);

        return new UserResponseVO(token, Authorities.USER);
    }

    /**
     * 관리자 권한 등록
     *
     * @return access token, 권한
     */
    @PutMapping(value = "/register/admin")
    public UserResponseVO registerAdmin(/* 뭐가 와야할까..?*/) {
        Long userId = SecurityUtil.getCurrentUser().getId();

        String token = userService.addAdminAuthority(userId);
        return new UserResponseVO(token, Authorities.ADMIN);
    }
}