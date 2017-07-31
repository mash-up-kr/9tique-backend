package kr.co.mash_up.nine_tique.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.mash_up.nine_tique.security.SecurityUtil;
import kr.co.mash_up.nine_tique.service.UserService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.web.dto.UserDto;
import kr.co.mash_up.nine_tique.web.vo.DataResponseVO;
import kr.co.mash_up.nine_tique.web.vo.UserRequestVO;
import lombok.extern.slf4j.Slf4j;

import static kr.co.mash_up.nine_tique.util.Constant.RestEndpoint.API_USER;

/**
 * 유저에 관련된 request를 처리한다
 * <p>
 * Created by ethankim on 2016. 10. 8..
 */
@RestController
@RequestMapping(value = API_USER)
@Api(description = "유저", tags = {"user"})
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "유저 로그인 or 회원가입", notes = "로그인하고(유저 정보가 없다면 회원가입 후), access token을 발급한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "로그인 성공(토큰 발급)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping(value = "/login")
    public DataResponseVO<UserDto> login(@RequestBody UserRequestVO requestVO) {
        log.info("login - user {}", requestVO);

        ParameterUtil.checkParameterEmpty(requestVO.getUserName(), requestVO.getEmail(), requestVO.getType());
        UserDto userDto = userService.login(requestVO);
        return new DataResponseVO<>(userDto);
    }

    /**
     * 현재 사용되지 않는 기능
     */
    @ApiOperation(value = "관리자 권한 등록", notes = "유저를 관리자로 등록한다(현재 사용되지 않는 기능)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "관리자로 등록 성공(토큰 발급)"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PutMapping(value = "/register/admin")
    public DataResponseVO<UserDto> registerAdmin(/* 뭐가 와야할까..?*/) {
        Long userId = SecurityUtil.getCurrentUser().getId();
        log.info("registerAdmin - userId : {}", userId);

        UserDto userDto = userService.addAdminAuthority(userId);
        return new DataResponseVO<>(userDto);
    }

    /**
     * Access Token Refresh
     *
     * @param authHeader request header에 있는 access token
     * @return refresh된 access token
     */
    @ApiOperation(value = "Access Token 갱신", notes = "유저의 Access Token을 갱신한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Access Token 갱신 성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping(value = "/refresh")
    public DataResponseVO<UserDto> refreshToken(@RequestHeader(value = "Authorization") String authHeader) {
        log.info("refreshToken - authHeader : {}", authHeader);

        UserDto userDto = userService.tokenRefresh(authHeader);
        return new DataResponseVO<>(userDto);
    }
}
