package kr.co.mash_up_9tique.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SellerDto {

    @JsonProperty(value = "authenti_code")
    private String authentiCode;

    @JsonProperty(value = "shop")
    private ShopDto shopDto;

    @JsonProperty(value = "user")
    private UserDto userDto;

    public static class Builder {

        private ShopDto shopDto;
        private UserDto userDto;
        private String authentiCode = "";

        public SellerDto build() {
            SellerDto sellerDto = new SellerDto();
            sellerDto.setAuthentiCode(authentiCode);
            sellerDto.setShopDto(shopDto);
            sellerDto.setUserDto(userDto);
            return sellerDto;
        }

        public SellerDto.Builder withAuthentiCode(String authentiCode) {
            this.authentiCode = authentiCode;
            return this;
        }

        public SellerDto.Builder withShopDto(ShopDto shopDto) {
            this.shopDto = shopDto;
            return this;
        }

        public SellerDto.Builder withUserDto(UserDto userDto) {
            this.userDto = userDto;
            return this;
        }
    }
}
