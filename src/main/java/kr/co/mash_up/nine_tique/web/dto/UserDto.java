package kr.co.mash_up.nine_tique.web.dto;

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

    @JsonProperty(value = "profile_image_url")
    private String profileImageUrl;

    @JsonProperty(value = "token")
    private String accessToken;

    @JsonProperty(value = "level")
    private String authorityLevel;

    private UserDto(Builder builder) {
        this.name = builder.name;
        this.profileImageUrl = builder.profileImageUrl;
        this.accessToken = builder.accessToken;
        this.authorityLevel = builder.authorityLevel;
    }

    public static class Builder {

        private String name = "";

        private String profileImageUrl = "";

        private String accessToken = "";

        private String authorityLevel = "";

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder authorityLevel(String authorityLevel) {
            this.authorityLevel = authorityLevel;
            return this;
        }

        public Builder profileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
