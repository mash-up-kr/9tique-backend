package kr.co.mash_up.nine_tique.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SellerInfoDto {

    @JsonProperty(value = "shop_name")
    private String shopName;

    @JsonProperty(value = "shop_info")
    private String shopInfo;

    @JsonProperty(value = "phone")
    private String phone;

    public static class Builder {

        private String shopName;
        private String shopInfo;
        private String phone;

        public SellerInfoDto build(){
            SellerInfoDto sellerInfoDto = new SellerInfoDto();
            sellerInfoDto.setShopName(shopName);
            sellerInfoDto.setShopInfo(shopInfo);
            sellerInfoDto.setPhone(phone);
            return sellerInfoDto;
        }

        public Builder withShopName(String shopName){
            this.shopName = shopName;
            return this;
        }

        public Builder withShopInfo(String shopInfo){
            this.shopInfo = shopInfo;
            return this;
        }

        public Builder withPhone(String phone){
            this.phone = phone;
            return this;
        }
    }
}
