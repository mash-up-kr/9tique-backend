package kr.co.mash_up.nine_tique.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class SellerRequestVO extends RequestVO {

    @JsonProperty("seller_name")
    private String sellerName;

    @JsonProperty("shop_name")
    private String shopName;

    @JsonProperty("shop_info")
    private String shopInfo;

    @JsonProperty("shop_phone")
    private String shopPhone;

    public Shop toShopEntity() {
        Shop shop = new Shop();
        shop.setName(shopName);
        shop.setDescription(shopInfo);
        shop.setPhoneNumber(shopPhone);
        return shop;
    }
}