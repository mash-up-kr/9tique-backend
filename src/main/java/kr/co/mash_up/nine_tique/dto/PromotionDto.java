package kr.co.mash_up.nine_tique.dto;

import java.sql.Timestamp;
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

    @JsonProperty("description")
    private String description;

    @JsonProperty("priority")
    private Integer priority;

    @JsonProperty("register")
    private String register;

    @JsonProperty("start_at")
    private Timestamp startAt;

    @JsonProperty("end_at")
    private Timestamp endAt;

    @JsonProperty("images")
    private List<ImageDto> promotionImages;

    @JsonProperty("products")
    private List<ProductDto> promotionProducts;
}
