package kr.co.mash_up.nine_tique.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 댓글에 대한 데이터 전달을 담당한다
 * <p>
 * Created by ethankim on 2017. 7. 9..
 */
@Getter
@Setter
@ToString
public class CommentDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty
    private String contents;

    @JsonProperty(value = "writer_name")
    private String writerName;

    private CommentDto(Builder builder) {
        this.id = builder.id;
        this.contents = builder.contents;
        this.writerName = builder.writerName;
    }

    public static class Builder {

        private Long id = 0L;

        private String contents = "";

        private String writerName = "";

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public Builder writerName(String writerName) {
            this.writerName = writerName;
            return this;
        }

        public CommentDto build() {
            return new CommentDto(this);
        }
    }
}
