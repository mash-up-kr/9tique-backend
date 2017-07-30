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
public class SellerDto {

    @JsonProperty(value = "authenti_code")
    private String authentiCode;

    @JsonProperty(value = "shop")
    private ShopDto shop;

    @JsonProperty(value = "user")
    private UserDto user;

    private SellerDto(Builder builder) {
        this.authentiCode = builder.authentiCode;
        this.shop = builder.shop;
        this.user = builder.user;
    }

    public static class Builder {

        private ShopDto shop;

        private UserDto user;

        private String authentiCode = "";

        public Builder authentiCode(String authentiCode) {
            this.authentiCode = authentiCode;
            return this;
        }

        public Builder shop(ShopDto shop) {
            this.shop = shop;
            return this;
        }

        public Builder user(UserDto user) {
            this.user = user;
            return this;
        }

        public SellerDto build() {
            return new SellerDto(this);
        }
    }
}
