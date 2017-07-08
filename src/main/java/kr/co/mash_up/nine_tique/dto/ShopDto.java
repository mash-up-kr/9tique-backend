package kr.co.mash_up.nine_tique.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 매장에 대한 데이터 전달을 담당한다
 * <p>
 * Created by ethankim on 2017. 7. 9..
 */
@Getter
@Setter
@ToString
public class ShopDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "kakao_open_chat_url")
    private String kakaoOpenChatUrl;

    private ShopDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.phoneNumber = builder.phoneNumber;
        this.kakaoOpenChatUrl = builder.kakaoOpenChatUrl;
    }

    public static class Builder {

        private Long id = 0L;

        private String name = "";

        private String description = "";

        private String phoneNumber = "";

        private String kakaoOpenChatUrl = "";

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder kakaoOpenChatUrl(String kakaoOpenChatUrl) {
            this.kakaoOpenChatUrl = kakaoOpenChatUrl;
            return this;
        }

        public ShopDto build() {
            return new ShopDto(this);
        }
    }
}
