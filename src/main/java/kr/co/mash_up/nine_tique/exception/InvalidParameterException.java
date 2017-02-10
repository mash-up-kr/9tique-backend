package kr.co.mash_up.nine_tique.exception;

import kr.co.mash_up.nine_tique.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 파라미터 잘못넣었을 경우 발생
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Parameter")
public class InvalidParameterException extends BaseException {


    public InvalidParameterException() {
        this("Invalid Parameter");
    }

    public InvalidParameterException(String message) {
        super(Constant.ResultCodes.BAD_REQUEST, message);
    }
}