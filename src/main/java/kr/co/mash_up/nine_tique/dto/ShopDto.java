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
public class ShopDto {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "info")
    private String info;

    @JsonProperty(value = "phone")
    private String phone;

    public static class Builder {

        private String name;
        private String info;
        private String phone;

        public ShopDto build(){
            ShopDto shopDto = new ShopDto();
            shopDto.setName(name);
            shopDto.setInfo(info);
            shopDto.setPhone(phone);
            return shopDto;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withInfo(String info){
            this.info = info;
            return this;
        }

        public Builder withPhone(String phone){
            this.phone = phone;
            return this;
        }
    }
}
