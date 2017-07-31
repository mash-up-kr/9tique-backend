package kr.co.mash_up.nine_tique.web.vo;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.web.dto.ImageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ethankim on 2017. 7. 9..
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PromotionRequestVO {

    private String name;

    private String description;

    private Integer priority;

    @JsonProperty(value = "start_at")
    private Timestamp startAt;

    @JsonProperty(value = "end_at")
    private Timestamp endAt;

    private List<ProductRequestVO> products;

    private List<ImageDto> images;
}
