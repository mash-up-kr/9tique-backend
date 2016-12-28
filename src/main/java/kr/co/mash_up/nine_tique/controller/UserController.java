package kr.co.mash_up.nine_tique.controller;

import kr.co.mash_up.nine_tique.security.Authorities;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.UserService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.vo.UserRequestVO;
import kr.co.mash_up.nine_tique.vo.UserResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserResponseVO login(@RequestBody UserRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getOauthToken(), requestVO.getType());

        // return access token
        String token = userService.login(requestVO);

        return new UserResponseVO(token, Authorities.USER);
    }

    /*
    인증 절차
    1. 존재하는 user가 인증코드를 보내면 seller info를 보내준다.
    request
        token - userid
        인증코드 - 검증용
    response
        seller info
        token
    2. seller info를 비교하여 업데이트 한다.
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.PUT)
    public UserResponseVO registerSeller(/*인증코드가 와야한다.*/) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        //Todo: 인증코드 passing
        String token = userService.addSellerAuthority(userId);

        return new UserResponseVO(token, Authorities.SELLER);
    }
}
