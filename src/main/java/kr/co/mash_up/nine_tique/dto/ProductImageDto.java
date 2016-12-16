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
public class ProductImageDto {

    @JsonProperty
    private String url;

    public static class Builder {
        private String url;

        public ProductImageDto build(){
            ProductImageDto productImageDto = new ProductImageDto();
            productImageDto.setUrl(url);
            return productImageDto;
        }

        public Builder withUrl(String url){
            this.url = url;
            return this;
        }
    }
}
