package kr.co.mash_up.nine_tique.exception;

import kr.co.mash_up.nine_tique.vo.ResultCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * user id가 맞지 않을 경우 발생
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "user id not matched")
public class UserIdNotMatchedException extends BaseException{

    public UserIdNotMatchedException(String message) {
        super(ResultCodes.BAD_REQUEST, message);
    }
}
