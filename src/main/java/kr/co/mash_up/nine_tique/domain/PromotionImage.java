package kr.co.mash_up.nine_tique.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;

import kr.co.mash_up.nine_tique.config.SystemPropertiesConfig;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ethankim on 2017. 7. 2..
 */
@Entity
@Table(name = "promotion_image")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class PromotionImage extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    //Todo: length 조정. 중복방지용, 36byte(32글자 + 확장자)
    @Column(name = "file_name", length = 255, nullable = false, unique = true)
    private String fileName;  // 업로드한 이미지 파일 이름(서버에서 중복되지 않게 재생성)

    // Todo: length 조정. 260byte(window 최대 256글자 + 확장자)
    @Column(name = "original_file_name", length = 255, nullable = false)
    private String originalFileName;  // 업로드한 이미지 파일 원본 이름

    @Column(name = "size", columnDefinition = "INT(11)")
    private Long size;  // 파일 사이즈

    @Column(name = "active", length = 1, columnDefinition = "VARCHAR(1)")
    @Type(type = "yes_no")
    private boolean active;

    // Todo: 클라우드 스토리지 이용시 사용 고려
//    @Column(length = 255, nullable = false)
//    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "promotion_id", foreignKey = @ForeignKey(name = "fk_promotion_image_to_promotion_id"))
    private Promotion promotion;

    /**
     * 임시 저장할 물리적인 image upload path 제공
     *
     * @return 임시 저장할 image upload path
     */
    public static String getImageUploadTempPath() {
        return String.format("%s/promotion/tmp", System.getProperty(SystemPropertiesConfig.STORAGE_PATH));
    }

    /**
     * 임시저장된 이미지를 다운받을 수 있는 url 제공
     *
     * @return temporarily image url
     */
    public String getTempImageUrl() {
        return String.format("%s/promotion/tmp/%s",
                System.getProperty(SystemPropertiesConfig.STORAGE_URI), this.fileName);
    }

    /**
     * 업로드된 이미지를 다운받을 수 있는 url 제공
     *
     * @return image url
     */
    @Transient  // 매핑하지 않는다.
    public String getImageUrl() {
        return String.format("%s/promotion/%d/%s",
                System.getProperty(SystemPropertiesConfig.STORAGE_URI), this.promotion.getId(), this.fileName);
    }

    /**
     * 물리적인 image upload path 제공
     *
     * @return upload path
     */
    @Transient  // 매핑하지 않는다.
    public String getImageUploadPath() {
        return String.format("%s/promotion/%d", System.getProperty(SystemPropertiesConfig.STORAGE_PATH), promotion.getId());
    }

    /**
     * url에서 file name 추출
     *
     * @param imageUrl image url
     * @return file name
     */
    @Transient
    public static String getFileNameFromUrl(String imageUrl) {
        // uri/promotion/tmp/fileName
        //Todo: confirm url pattern matching??
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }
}
