package kr.co.mash_up.nine_tique.exception;

import kr.co.mash_up.nine_tique.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 생성할 리소스가 이미 존재할 경우 발생
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Already Exist")
public class AlreadyExistException extends BaseException {

    public AlreadyExistException(String message) {
        super(Constant.ResultCodes.BAD_REQUEST, message);
    }
}
