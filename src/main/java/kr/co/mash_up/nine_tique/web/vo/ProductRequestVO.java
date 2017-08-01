package kr.co.mash_up.nine_tique.web.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.web.dto.ImageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductRequestVO extends RequestVO {

    @JsonProperty
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("brand_name_eng")
    private String brandNameEng;

    @JsonProperty("size")
    private String size;

    @JsonProperty("price")
    private int price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private Product.Status status;

    @JsonProperty("main_category")
    private String mainCategory;

    @JsonProperty("sub_category")
    private String subCategory;  // ""인것도 있어서 checkParameterEmpty()로 안넘긴다.

    @JsonProperty("product_images")
    private List<ImageDto> images;

    public Product toProductEntity() {
        Product product = new Product();
        product.setName(name);
        product.setSize(size);
        product.setPrice(price);
        product.setDescription(description);
        product.setStatus(Product.Status.SELL);
        product.setZzimCount(0L);
        return product;
    }
}
