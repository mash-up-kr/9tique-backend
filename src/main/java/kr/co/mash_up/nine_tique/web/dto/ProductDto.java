package kr.co.mash_up.nine_tique.web.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty(value = "brand")
    private BrandDto brand;

    @JsonProperty
    private String size;

    @JsonProperty
    private int price;

    @JsonProperty
    private String description;

    @JsonProperty
    private Product.Status status;

    @JsonProperty(value = "category")
    private CategoryDto categoryDto;

    @JsonProperty(value = "shop")
    private ShopDto shopDto;

    @JsonProperty(value = "images")
    private List<ImageDto> images;

    @JsonProperty(value = "zzim_status")
    private boolean zzimStatus;

    @JsonProperty(value = "seller")
    private boolean seller;

    public static class Builder {

        private Long id;

        private String name;

        private BrandDto brand;

        private String size;

        private int price;

        private String description;

        private Product.Status status;

        private CategoryDto category;

        private ShopDto shop;

        private List<ImageDto> images;

        private boolean zzimStatus;

        private boolean seller;

        public ProductDto build() {
            ProductDto productDto = new ProductDto();
            productDto.setId(id);
            productDto.setName(name);
            productDto.setBrand(brand);
            productDto.setSize(size);
            productDto.setPrice(price);
            productDto.setDescription(description);
            productDto.setStatus(status);
            productDto.setCategoryDto(category);
            productDto.setShopDto(shop);
            productDto.setImages(images);
            productDto.setZzimStatus(zzimStatus);
            productDto.setSeller(seller);
            return productDto;
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withBrand(BrandDto brand) {
            this.brand = brand;
            return this;
        }

        public Builder withSize(String size) {
            this.size = size;
            return this;
        }

        public Builder withPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withStatus(Product.Status status) {
            this.status = status;
            return this;
        }

        public Builder withCategory(CategoryDto category) {
            this.category = category;
            return this;
        }

        public Builder withShop(ShopDto shop) {
            this.shop = shop;
            return this;
        }

        public Builder withImages(List<ImageDto> images) {
            this.images = images;
            return this;
        }

        public Builder withZzimStatus(boolean zzimStatus) {
            this.zzimStatus = zzimStatus;
            return this;
        }

        public Builder withSeller(boolean seller) {
            this.seller = seller;
            return this;
        }
    }
}
