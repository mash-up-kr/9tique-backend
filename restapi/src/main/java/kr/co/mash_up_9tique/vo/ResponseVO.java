package kr.co.mash_up_9tique.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up_9tique.util.Constant;
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
    private int status;

    @JsonProperty
    private String message;

    public ResponseVO(int status){
        this.status = status;
    }

    public ResponseVO(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResponseVO ok(){
        return new ResponseVO(Constant.ResultCodes.OK, "success");
    }

    public static ResponseVO created(){
        return new ResponseVO(Constant.ResultCodes.CREATED, "created");
    }

    public static ResponseVO noContent(){
        return new ResponseVO(Constant.ResultCodes.NO_CONTENT, "no content");
    }
}
