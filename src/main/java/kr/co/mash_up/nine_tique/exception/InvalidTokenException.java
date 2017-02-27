package kr.co.mash_up.nine_tique.exception;


import kr.co.mash_up.nine_tique.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Token")
public class InvalidTokenException extends BaseException {

    public InvalidTokenException() {
        this("Invalid Token");
    }

    public InvalidTokenException(String message) {
        super(Constant.ResultCodes.UN_AUTHORIZED, message);
    }
}
