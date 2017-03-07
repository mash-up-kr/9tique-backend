package kr.co.mash_up.nine_tique.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "유저", tags = {"user"})
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 유저 로그인 or 회원가입
     * 유저 정보가 없다면 회원가입, 있다면 로그인하고 access token 발급
     *
     * @param requestVO 유저 정보
     * @return 생성 결과
     */
    @ApiOperation(value = "유저 로그인 or 회원가입")
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
    @ApiOperation(value = "관리자 권한 등록")
    @PutMapping(value = "/register/admin")
    public DataResponseVO<UserDto> registerAdmin(/* 뭐가 와야할까..?*/) {
        Long userId = SecurityUtil.getCurrentUser().getId();

        UserDto userDto = userService.addAdminAuthority(userId);
        return new DataResponseVO<UserDto>(userDto);
    }

    /**
     * Access Token Refresh
     * @param authHeader request header에 있는 access token
     * @return refresh된 access token
     */
    @ApiOperation(value = "Access Token Refresh")
    @GetMapping(value = "/refresh")
    public DataResponseVO<UserDto> refreshToken(@RequestHeader(value = "Authorization") String authHeader) {
        UserDto userDto = userService.tokenRefresh(authHeader);
        return new DataResponseVO<UserDto>(userDto);
    }
}