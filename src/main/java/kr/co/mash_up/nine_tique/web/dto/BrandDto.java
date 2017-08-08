package kr.co.mash_up.nine_tique.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.domain.Brand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 브랜드에 대한 데이터 전달을 담당한다
 * <p>
 * Created by ethankim on 2017. 8. 1..
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BrandDto {

    @JsonProperty(value = "brand_id")
    private Long brandId;

    @JsonProperty(value = "name_ko")
    private String nameKo;

    @JsonProperty(value = "name_eng")
    private String nameEng;

    public static BrandDto fromBrand(Brand brand) {
        BrandDto brandDto = new BrandDto();
        brandDto.setNameKo(brand.getNameKo());
        brandDto.setNameEng(brand.getNameEng());
        return brandDto;
    }

    private BrandDto(Builder builder) {
        this.brandId = builder.brandId;
        this.nameKo = builder.nameKo;
        this.nameEng = builder.nameEng;
    }

    public static class Builder {

        private long brandId = 0L;

        private String nameKo = "";

        private String nameEng = "";

        public Builder brandId(long brandId) {
            this.brandId = brandId;
            return this;
        }

        public Builder nameKo(String nameKo) {
            this.nameKo = nameKo;
            return this;
        }

        public Builder nameEng(String nameEng) {
            this.nameEng = nameEng;
            return this;
        }

        public BrandDto build() {
            return new BrandDto(this);
        }
    }
}
