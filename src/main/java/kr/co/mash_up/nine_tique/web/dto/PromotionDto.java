package kr.co.mash_up.nine_tique.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 프로모션에 대한 데이터 전달을 담당한다
 * <p>
 * Created by ethankim on 2017. 7. 9..
 */
@Getter
@Setter
@ToString
public class PromotionDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("images")
    private List<ImageDto> images;

    private PromotionDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.images = builder.images;
    }

    public static class Builder {

        private Long id = 0L;

        private String name = "";

        private List<ImageDto> images = null;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder images(List<ImageDto> images) {
            this.images = images;
            return this;
        }

        public PromotionDto build() {
            return new PromotionDto(this);
        }
    }
}
