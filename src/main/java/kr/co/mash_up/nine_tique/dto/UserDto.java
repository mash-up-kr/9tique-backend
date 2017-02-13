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

    public static class Builder {
        private String name = "";

        public UserDto build() {
            UserDto userDto = new UserDto();
            userDto.setName(name);
            return userDto;
        }

        public UserDto.Builder withName(String name) {
            this.name = name;
            return this;
        }
    }
}
