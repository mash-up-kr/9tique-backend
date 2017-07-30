package kr.co.mash_up.nine_tique.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthentiCodeRequestVO extends RequestVO {

    @JsonProperty(value = "authenti_code")
    private String authentiCode;
}
