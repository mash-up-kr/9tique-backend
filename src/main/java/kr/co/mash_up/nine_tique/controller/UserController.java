package kr.co.mash_up.nine_tique.controller;

import kr.co.mash_up.nine_tique.service.UserService;
import kr.co.mash_up.nine_tique.util.ParameterUtil;
import kr.co.mash_up.nine_tique.vo.UserRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody UserRequestVO requestVO) {
        ParameterUtil.checkParameterEmpty(requestVO.getOauthToken(), requestVO.getType());

        //Todo: return access token
        String token = userService.login(requestVO);

        return token;
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.PUT)
    public String registerSeller(@PathVariable("id") Long id){
        String token = userService.addSellerAuthority(id);

        return token;
    }


}
