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

    @JsonProperty(value = "shop")
    private ShopDto shopDto;

    @JsonProperty(value = "images")
    private List<ImageDto> images;

    @JsonProperty(value = "zzim_status")
    private boolean zzimStatus;

    @JsonProperty(value = "created_at")
    private long createdAt;

    @JsonProperty(value = "updated_at")
    private long updatedAt;

    @JsonProperty(value = "seller")
    private boolean seller;

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
        private ShopDto shop;
        private List<ImageDto> images;
        private boolean zzimStatus;
        private long createdAt;
        private long updatedAt;
        private boolean seller;

        public ProductDto build() {
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
            productDto.setShopDto(shop);
            productDto.setImages(images);
            productDto.setZzimStatus(zzimStatus);
            productDto.setCreatedAt(createdAt);
            productDto.setUpdatedAt(updatedAt);
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

        public Builder withBrandName(String brandName) {
            this.brandName = brandName;
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

        public Builder withMainCategory(String mainCategory) {
            this.mainCategory = mainCategory;
            return this;
        }

        public Builder withSubCategory(String subCategory) {
            this.subCategory = subCategory;
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

        public Builder withCreatedAt(long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withUpdatedAt(long updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder withSeller(boolean seller) {
            this.seller = seller;
            return this;
        }
    }
}
