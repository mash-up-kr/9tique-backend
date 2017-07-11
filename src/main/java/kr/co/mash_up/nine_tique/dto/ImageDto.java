package kr.co.mash_up.nine_tique.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Image에 대한 데이터 전달을 담당한다
 * <p>
 * Created by ethankim on 2017. 7. 9..
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ImageDto {

    @JsonProperty
    private String url;

    private ImageDto(Builder builder) {
        this.url = builder.url;
    }

    public static class Builder {

        private String url = "";

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public ImageDto build() {
            return new ImageDto(this);
        }
    }
}
