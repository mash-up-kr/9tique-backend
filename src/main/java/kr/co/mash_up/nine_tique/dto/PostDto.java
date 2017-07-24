package kr.co.mash_up.nine_tique.dto;

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





}
