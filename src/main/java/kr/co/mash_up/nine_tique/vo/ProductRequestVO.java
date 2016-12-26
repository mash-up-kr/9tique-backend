package kr.co.mash_up.nine_tique.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductRequestVO extends RequestVO {

    @JsonProperty
    private String name;

    @JsonProperty
    private String brandName;

    @JsonProperty
    private String size;

    @JsonProperty
    private int price;

    @JsonProperty
    private String description;

    @JsonProperty
    private String productStatus;

    @JsonProperty
    private String mainCategory;

    @JsonProperty
    private String subCategory;  // ""인것도 있어서 checkParameterEmpty()로 안넘긴다.

    @JsonIgnore  // MultipartFile을 json으로 serialization할 수 없기 때문에 무시
    private List<MultipartFile> files;

    public Product toProductEntity() {
        Product product = new Product();
        product.setName(name);
        product.setBrandName(brandName);
        product.setSize(size);
        product.setPrice(price);
        product.setDescription(description);
        return product;
    }
}
