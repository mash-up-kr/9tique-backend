package kr.co.mash_up_9tique.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up_9tique.domain.Shop;
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

    @JsonProperty(value = "kakao_open_chat_url")
    private String kakaoOpenChatUrl;

    public Shop toShopEntitiy() {
        Shop shop = new Shop();
        shop.setName(this.name);
        shop.setInfo(this.info);
        shop.setPhone(this.phone);
        shop.setKakaoOpenChatUrl(this.kakaoOpenChatUrl);
        shop.setEnabled(true);
        return shop;
    }
}
