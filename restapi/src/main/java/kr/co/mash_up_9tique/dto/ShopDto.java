package kr.co.mash_up_9tique.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ShopDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "info")
    private String info;

    @JsonProperty(value = "phone")
    private String phone;

    @JsonProperty(value = "kakao_open_chat_url")
    private String kakaoOpenChatUrl;

    public static class Builder {

        private Long id = 0L;
        private String name = "";
        private String info = "";
        private String phone = "";
        private String kakaoOpenChatUrl = "";

        public ShopDto build() {
            ShopDto shopDto = new ShopDto();
            shopDto.setId(id);
            shopDto.setName(name);
            shopDto.setInfo(info);
            shopDto.setPhone(phone);
            shopDto.setKakaoOpenChatUrl(kakaoOpenChatUrl);
            return shopDto;
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withInfo(String info) {
            this.info = info;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withKakaoOpenChatUrl(String kakaoOpenChatUrl) {
            this.kakaoOpenChatUrl = kakaoOpenChatUrl;
            return this;
        }
    }
}
