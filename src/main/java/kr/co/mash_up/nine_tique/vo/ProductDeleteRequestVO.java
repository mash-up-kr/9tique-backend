package kr.co.mash_up.nine_tique.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDeleteRequestVO extends RequestVO {

    List<ProductRequestVO> products;
}
