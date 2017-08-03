package kr.co.mash_up.nine_tique.web.vo;

import kr.co.mash_up.nine_tique.domain.Brand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Brand View
 * <p>
 * Created by ethankim on 2017. 7. 3..
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BrandRequestVO {

    private String nameKo;

    private String nameEng;

    public Brand toBrandEntity() {
        Brand brand = new Brand();
        brand.setNameKo(this.nameKo);
        brand.setNameEng(this.nameEng);

        return brand;
    }
}
