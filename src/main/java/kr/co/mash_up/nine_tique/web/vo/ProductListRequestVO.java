package kr.co.mash_up.nine_tique.web.vo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductListRequestVO extends DataListRequestVO {

    private String mainCategory = "";

    private String subCategory = "";
}
