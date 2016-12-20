package kr.co.mash_up.nine_tique.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDto {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty(value = "brand_name")
    private String brandName;

    @JsonProperty
    private String size;

    @JsonProperty
    private int price;

    @JsonProperty
    private String description;

    @JsonProperty
    private Product.Status status;

    @JsonProperty(value = "main_category")
    private String mainCategory;

    @JsonProperty(value = "sub_category")
    private String subCategory;

    @JsonProperty(value = "seller_info")
    private SellerInfoDto sellerInfoDto;

    @JsonProperty(value = "product_images")
    private List<ProductImageDto> productImageDtos;

    public static class Builder {

        private Long id;
        private String name;
        private String brandName;
        private String size;
        private int price;
        private String description;
        private Product.Status status;
        private String mainCategory;
        private String subCategory;
        private SellerInfoDto sellerInfo;
        private List<ProductImageDto> productImages;

        public ProductDto build(){
            ProductDto productDto = new ProductDto();
            productDto.setId(id);
            productDto.setName(name);
            productDto.setBrandName(brandName);
            productDto.setSize(size);
            productDto.setPrice(price);
            productDto.setDescription(description);
            productDto.setStatus(status);
            productDto.setMainCategory(mainCategory);
            productDto.setSubCategory(subCategory);
            productDto.setSellerInfoDto(sellerInfo);
            productDto.setProductImageDtos(productImages);
            return productDto;
        }

        public Builder withId(Long id){
            this.id = id;
            return this;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withBrandName(String brandName){
            this.brandName = brandName;
            return this;
        }

        public Builder withSize(String size){
            this.size = size;
            return this;
        }

        public Builder withPrice(int price){
            this.price = price;
            return this;
        }

        public Builder withDescription(String description){
            this.description = description;
            return this;
        }

        public Builder withStatus(Product.Status status){
            this.status = status;
            return this;
        }

        public Builder withMainCategory(String mainCategory){
            this.mainCategory = mainCategory;
            return this;
        }

        public Builder withSubCategory(String subCategory){
            this.subCategory = subCategory;
            return this;
        }

        public Builder withSellerInfo(SellerInfoDto sellerInfo){
            this.sellerInfo = sellerInfo;
            return this;
        }

        public Builder withProductImages( List<ProductImageDto> productImages){
            this.productImages = productImages;
            return this;
        }
    }
}