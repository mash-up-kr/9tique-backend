package kr.co.mash_up_9tique.exception;

import kr.co.mash_up_9tique.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 리소스가 존재하지 않을 경우 발생
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
public class IdNotFoundException extends BaseException {

    public IdNotFoundException(String message) {
        super(Constant.ResultCodes.NOT_FOUND, message);
    }
}
