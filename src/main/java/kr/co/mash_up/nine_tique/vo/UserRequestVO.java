package kr.co.mash_up.nine_tique.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRequestVO extends RequestVO{

    @JsonProperty(value = "oauth_token")
    private String oauthToken;

    @JsonProperty(value = "type")
    private User.OauthType type;

    public User toUserEntity(){
        User user = new User();
        user.setOauthToken(oauthToken);
        user.setOauthType(type);
        return user;
    }

}
