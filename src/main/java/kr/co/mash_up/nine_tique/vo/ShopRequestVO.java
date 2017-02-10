package kr.co.mash_up.nine_tique.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShopRequestVO extends RequestVO {

    @JsonProperty
    private String name;

    @JsonProperty
    private String info;

    @JsonProperty
    private String phone;

    public Shop toShopEntitiy() {
        Shop shop = new Shop();
        shop.setName(this.name);
        shop.setInfo(this.info);
        shop.setPhone(this.phone);
        shop.setEnabled(true);
        return shop;
    }
}
