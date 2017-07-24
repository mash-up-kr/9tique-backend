package kr.co.mash_up.nine_tique.vo;

import java.util.List;

import kr.co.mash_up.nine_tique.dto.ImageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ethankim on 2017. 7. 24..
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostRequestVO {

    private String name;

    private String contents;

    private List<ProductRequestVO> products;

    private List<ImageDto> images;
}
