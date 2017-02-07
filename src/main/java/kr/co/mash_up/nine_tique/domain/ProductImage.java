package kr.co.mash_up.nine_tique.domain;


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
@ToString(exclude = {"product"})
@NoArgsConstructor  // JPA는 default constructor 필요
public class ProductImage extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // id의 수동적인 제어를 막기 위해 setter를 생성하지 않는다.

    @Column(length = 255, nullable = false, unique = true)
    private String fileName;  //Todo:length 조정. 중복방지용, 36byte(32글자 + 확장자)

    @Column(length = 255, nullable = false)
    private String originalFileName;  // Todo:length 조정. 260byte(window 최대 256글자 + 확장자)

    @Column
    private long size;

    @Column
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "product_id")  // FK 매핑시 이용
    private Product product;  // 상품

//    @Column(length = 255, nullable = false)
//    private String imageUrl;

    /**
     * 임시 저장할 물리적인 image upload path 제공
     *
     * @return 임시 저장할 image upload path
     */
    public static String getImageUploadTempPath() {
        return String.format("%s/product/tmp", System.getProperty(SystemPropertiesConfig.STORAGE_PATH));
    }

    /**
     * 임시저장된 이미지를 다운받을 수 있는 url 제공
     *
     * @return temporarily image url
     */
    public String getTempImageUrl() {
        return String.format("%s/product/tmp/%s",
                System.getProperty(SystemPropertiesConfig.STORAGE_URI), this.fileName);
    }

    /**
     * 업로드된 이미지를 다운받을 수 있는 url 제공
     *
     * @return image url
     */
    @Transient  // 매핑하지 않는다.
    public String getImageUrl() {
        return String.format("%s/product/%d/%s",
                System.getProperty(SystemPropertiesConfig.STORAGE_URI), this.product.getId(), this.fileName);
    }

    /**
     * 물리적인 image upload path 제공
     *
     * @return upload path
     */
    @Transient  // 매핑하지 않는다.
    public String getImageUploadPath() {
        return String.format("%s/product/%d", System.getProperty(SystemPropertiesConfig.STORAGE_PATH), product.getId());
    }

    /**
     * url에서 file name 추출
     *
     * @param imageUrl image url
     * @return file name
     */
    @Transient
    public static String getFileNameFromUrl(String imageUrl) {
        // uri/product/tmp/fileName
        //Todo: confirm url pattern matching??
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }

    public void disable() {
        if (enabled) {
            this.enabled = false;
        }
    }

    public void enable() {
        if (!enabled) {
            this.enabled = true;
        }
    }
}
