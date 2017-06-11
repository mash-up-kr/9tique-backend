package kr.co.mash_up_9tique.exception;

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

    private int status;

    public BaseException(int status) {
        this(status, null);
    }

    public BaseException(int status, String debugMessage) {
        this(status, debugMessage, null);
    }

    public BaseException(int status, String debugMessage, Throwable throwable) {
        super(debugMessage, throwable);
        this.status = status;
    }

    public String getExceptionDebugMessage() {
        return this.toString();
    }
}
