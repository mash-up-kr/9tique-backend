package kr.co.mash_up.nine_tique.dto;


import java.util.Collections;
import java.util.List;

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

    @JsonProperty(value = "images")
    private List<ImageDto> images;

    private ShopDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.phoneNumber = builder.phoneNumber;
        this.kakaoOpenChatUrl = builder.kakaoOpenChatUrl;
        this.images = builder.images;
    }

    public static class Builder {

        private Long id = 0L;

        private String name = "";

        private String description = "";

        private String phoneNumber = "";

        private String kakaoOpenChatUrl = "";

        private List<ImageDto> images = Collections.emptyList();

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

        public Builder images(List<ImageDto> images) {
            this.images = images;
            return this;
        }

        public ShopDto build() {
            return new ShopDto(this);
        }
    }
}
