package kr.co.mash_up.nine_tique.web.vo;

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
public class UserRequestVO extends RequestVO {

//    @JsonProperty(value = "oauth_token")
//    private String oauthToken;

    @JsonProperty(value = "type")
    private User.OauthType type;

    @JsonProperty(value = "name")
    private String userName;

    @JsonProperty(value = "email")
    private String email;

    public User toUserEntity() {
        User user = new User();
//        user.setOauthToken(oauthToken);
        user.setName(userName);
        user.setEmail(email);
        user.setOauthType(type);
        return user;
    }
}