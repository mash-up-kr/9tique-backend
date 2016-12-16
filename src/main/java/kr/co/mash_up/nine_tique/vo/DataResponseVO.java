package kr.co.mash_up.nine_tique.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 응답코드/메시지 + 1개의 데이터(엔티티, VO)가 있는 경우
 * @param <T> data 클래스
 */
@Setter
@Getter
@ToString
public class DataResponseVO<T> extends ResponseVO {

    private T item;

    public DataResponseVO(String resultCode){
        super(resultCode);
    }

    public DataResponseVO(String resultCode, String message){
        super(resultCode, message);
    }

    public DataResponseVO(T item){
        this(ResultCodes.OK,  "success");
        this.item = item;
    }
}
