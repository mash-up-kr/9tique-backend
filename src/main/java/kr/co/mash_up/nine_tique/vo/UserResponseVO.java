package kr.co.mash_up.nine_tique.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserResponseVO extends ResponseVO {

    @JsonProperty
    private String token;

    @JsonProperty
    private String level;

    public UserResponseVO(Integer status) {
        super(status);
    }

    public UserResponseVO(String token, String level) {
        super(ResultCodes.OK, "success");
        this.token = token;
        this.level = level;
    }
}
