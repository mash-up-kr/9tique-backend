package kr.co.mash_up.nine_tique.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryRequestVO extends RequestVO {

    @JsonProperty
    private String main;

    @JsonProperty
    private String sub;

    public Category toCategoryEntity(){
        Category category = new Category();
        category.setMain(this.main);
        category.setSub(this.sub);
        category.setEnabled(true);
        return category;
    }
}
