package kr.co.mash_up.nine_tique.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시물에 대한 데이터 전달을 담당한다
 * <p>
 * Created by ethankim on 2017. 7. 24..
 */
@Getter
@Setter
@ToString
public class PostDto {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;  // HTML 형식

    @JsonProperty
    private String contents;  // HTML 형식

    @JsonProperty
    private List<ImageDto> images;

    private PostDto(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.contents = builder.contents;
        this.images = builder.images;
    }

    public static class Builder {

        private Long id;

        private String name;

        private String contents;

        private List<ImageDto> images;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public Builder images(List<ImageDto> images) {
            this.images = images;
            return this;
        }

        public PostDto build() {
            return new PostDto(this);
        }
    }
}
