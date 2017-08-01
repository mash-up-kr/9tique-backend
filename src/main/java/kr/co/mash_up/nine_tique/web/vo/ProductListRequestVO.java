package kr.co.mash_up.nine_tique.web.vo;

import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;



@Setter
public class ProductListRequestVO extends DataListRequestVO {

    private String mainCategory = "";

    private String subCategory = "";

    public String getMainCategory() {
        return mainCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }
}
