package kr.co.mash_up.nine_tique.controller;

import kr.co.mash_up.nine_tique.dto.UserDto;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.UserService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.vo.UserRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_USER;

@RestController
@RequestMapping(value = API_USER)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public DataResponseVO<UserDto> login(@RequestBody UserRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getUserName(), requestVO.getEmail(), requestVO.getType());

        UserDto userDto = userService.login(requestVO);

        return new DataResponseVO<UserDto>(userDto);
    }

    /**
     * 관리자 권한 등록
     *
     * @return access token, 권한
     */
    @PutMapping(value = "/register/admin")
    public DataResponseVO<UserDto> registerAdmin(/* 뭐가 와야할까..?*/) {
        Long userId = SecurityUtil.getCurrentUser().getId();

        UserDto userDto = userService.addAdminAuthority(userId);
        return new DataResponseVO<UserDto>(userDto);
    }

    @GetMapping(value = "/refresh")
    public DataResponseVO<UserDto> refreshToken(@RequestHeader(value = "Authorization") String authHeader) {
        UserDto userDto = userService.tokenRefresh(authHeader);
        return new DataResponseVO<UserDto>(userDto);
    }
}