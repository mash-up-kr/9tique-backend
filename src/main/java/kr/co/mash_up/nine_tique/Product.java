package kr.co.mash_up.nine_tique;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.javafx.runtime.SystemProperties;
import kr.co.mash_up.nine_tique.config.SystemPropertiesConfig;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@NoArgsConstructor  // JPA는 default constructor 필요
public class Product extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;  // id의 수동적인 제어를 막기 위해 setter를 생성하지 않는다.

    @Column(length = 50)
    @JsonProperty
    private String name;  //이름

    @Lob  // text type으로 사용하기 위해
    @JsonProperty
    private String description;  // 상세설명

    @Lob
    private List<String> imageFileName;  //Todo: 이미지 5개로 제한

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;  // 판매중/완료

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_info_id")
    private SellerInfo sellerInfo;  // 판매자 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")  // FK 매핑시 이용
    private Category category;  // 카테고리

    //Todo: 이벤트 여부 추가?

    /**
     * test등의 용도로 setter 생성
     * @param id
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * 업로드된 이미지를 다운받을 수 있는 url 제공
     * @return image url
     */
    @JsonProperty("imageUrl")
    public String getImageUrl(){
        return String.format("$s/product/%d/%s",
                System.getProperty(SystemPropertiesConfig.STORAGE_URI), this.id, this.imageFileName);
    }

    /**
     * 물리적인 image upload path 제공
     * @return upload path
     */
    @Transient
    public String getImageUploadPath(){
        return String.format("%s/product/%d", System.getProperty(SystemPropertiesConfig.STORAGE_PATH), this.id);
    }
}
