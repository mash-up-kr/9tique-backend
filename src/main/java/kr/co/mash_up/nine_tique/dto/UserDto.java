package kr.co.mash_up.nine_tique.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "token")
    private String accessToken;

    @JsonProperty(value = "level")
    private String authorityLevel;

    public static class Builder {
        private String name = "";
        private String accessToken = "";
        private String authorityLevel = "";

        public UserDto build() {
            UserDto userDto = new UserDto();
            userDto.setName(name);
            userDto.setAccessToken(accessToken);
            userDto.setAuthorityLevel(authorityLevel);
            return userDto;
        }

        public UserDto.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public UserDto.Builder withAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public UserDto.Builder withAuthorityLevel(String authorityLevel) {
            this.authorityLevel = authorityLevel;
            return this;
        }
    }
}
