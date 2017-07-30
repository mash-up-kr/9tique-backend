package kr.co.mash_up.nine_tique.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 카테고리에 대한 데이터 전달을 담당한다
 * <p>
 * Created by ethankim on 2017. 7. 29..
 */
@Getter
@Setter
@ToString
public class CategoryDto {

    // 상위 카테고리 이름
    @JsonProperty
    private String main;

    // 하위 카테고리 이름
    @JsonProperty
    private String sub;

    public static CategoryDto fromCategory(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setMain(category.getMain());
        categoryDto.setSub(category.getSub());
        return categoryDto;
    }
}
