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

    private final String resultCode;

    public BaseException(String resultCode) {
        this(resultCode, null);
    }

    public BaseException(String resultCode, String debugMessage) {
        this(resultCode, debugMessage, null);
    }

    public BaseException(String resultCode, String debugMessage, Throwable throwable) {
        super(debugMessage, throwable);
        this.resultCode = resultCode;
    }

    public String getExceptionDebugMessage() {
        return this.toString();
    }
}
