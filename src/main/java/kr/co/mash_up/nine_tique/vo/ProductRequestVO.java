package kr.co.mash_up.nine_tique.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.Product;
import kr.co.mash_up.nine_tique.dto.ImageDto;
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

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("size")
    private String size;

    @JsonProperty("price")
    private int price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private String status;

    @JsonProperty("main_category")
    private String mainCategory;

    @JsonProperty("sub_category")
    private String subCategory;  // ""인것도 있어서 checkParameterEmpty()로 안넘긴다.

    @JsonProperty("product_images")
    private List<ImageDto> images;

    public Product toProductEntity() {
        Product product = new Product();
        product.setName(name);
//        product.getBrand(brandName);  Todo: 수정
        product.setSize(size);
        product.setPrice(price);
        product.setDescription(description);
        product.setStatus(getStatus());

        return product;
    }

    public Product.Status getStatus() {
        if (status != null && status.equalsIgnoreCase("SELL")) {
            return Product.Status.SELL;
        } else if (status != null && status.equalsIgnoreCase("SOLD_OUT")) {
            return Product.Status.SOLD_OUT;
        }
        return null;
    }
}
