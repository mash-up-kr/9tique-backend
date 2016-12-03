package kr.co.mash_up.nine_tique.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.config.SystemPropertiesConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product_image")
@Getter
@Setter
@ToString
@NoArgsConstructor  // JPA는 default constructor 필요
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;  // id의 수동적인 제어를 막기 위해 setter를 생성하지 않는다.

    @Column(length = 255, nullable = false, unique = true)
    private String imageFileName;

    //Todo: imageUrl을 @Column으로 추가

    @ManyToOne
    @JoinColumn(name = "product_id")  // FK 매핑시 이용
    private Product product;  // 상품

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
