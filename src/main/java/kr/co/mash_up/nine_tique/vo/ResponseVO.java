package kr.co.mash_up.nine_tique.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 응답 처리를 위한 클래스
 * 응답코드/메시지만 있는 경우
 */
@Getter
@Setter
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(value = JsonInclude.Include.ALWAYS)
public class ResponseVO {

    @JsonProperty
    private String resultCode;

    @JsonProperty
    private String message;

    public ResponseVO(String resultCode){
        this.resultCode = resultCode;
    }

    public ResponseVO(String resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public static ResponseVO ok(){
        return new ResponseVO(ResultCodes.OK);
    }
}
