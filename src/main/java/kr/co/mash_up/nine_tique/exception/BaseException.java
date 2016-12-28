package kr.co.mash_up.nine_tique.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 예외처리 클래스
 * 모든 비즈니스 예외는 BaseException을 상속받아 구현
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class BaseException extends RuntimeException {

    private final Integer status;

    public BaseException(Integer status) {
        this(status, null);
    }

    public BaseException(Integer status, String debugMessage) {
        this(status, debugMessage, null);
    }

    public BaseException(Integer status, String debugMessage, Throwable throwable) {
        super(debugMessage, throwable);
        this.status = status;
    }

    public String getExceptionDebugMessage() {
        return this.toString();
    }
}
