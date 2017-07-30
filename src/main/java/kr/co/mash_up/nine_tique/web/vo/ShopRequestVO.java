package kr.co.mash_up.nine_tique.web.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.Shop;
import kr.co.mash_up.nine_tique.web.dto.ImageDto;
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
    private String description;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "kakao_open_chat_url")
    private String kakaoOpenChatUrl;

    @JsonProperty
    private List<ImageDto> images;

    public Shop toShopEntitiy() {
        Shop shop = new Shop();
        shop.setName(this.name);
        shop.setDescription(this.description);
        shop.setPhoneNumber(this.phoneNumber);
        shop.setKakaoOpenChatUrl(this.kakaoOpenChatUrl);
        return shop;
    }
}
