package kr.co.mash_up.nine_tique.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ethankim on 2017. 7. 2..
 */
@Entity
@Table(name = "post_image")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class PostImage extends AbstractEntity<PostImage.Id> {

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne  // PostImage(Many) : Image(One)
    @MapsId
    private Image image;

    @ManyToOne  // PostImage(Many) : Post(One)
    @MapsId
    private Post post;

    @Column(name = "active", length = 1, columnDefinition = "VARCHAR(1)")
    @Type(type = "yes_no")
    private boolean active;

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"postId", "imageId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "post_id", columnDefinition = "INT(11)")
        private Long postId;

        @Column(name = "image_id", columnDefinition = "INT(11)")
        private Long imageId;
    }

    public PostImage(Post post, Image image) {
        this.id.postId = post.getId();
        this.id.imageId = image.getId();
        this.post = post;
        this.image = image;
    }

//    /**
//     * 임시 저장할 물리적인 image upload path 제공
//     *
//     * @return 임시 저장할 image upload path
//     */
//    public static String getImageUploadTempPath() {
//        return String.format("%s/product/tmp", System.getProperty(SystemPropertiesConfig.STORAGE_PATH));
//    }
//
//    /**
//     * 임시저장된 이미지를 다운받을 수 있는 url 제공
//     *
//     * @return temporarily image url
//     */
//    public String getTempImageUrl() {
//        return String.format("%s/product/tmp/%s",
//                System.getProperty(SystemPropertiesConfig.STORAGE_URI), this.fileName);
//    }
//
//    /**
//     * 업로드된 이미지를 다운받을 수 있는 url 제공
//     *
//     * @return image url
//     */
//    @Transient  // 매핑하지 않는다.
//    public String getImageUrl() {
//        return String.format("%s/product/%d/%s",
//                System.getProperty(SystemPropertiesConfig.STORAGE_URI), this.post.getId(), this.fileName);
//    }
//
//    /**
//     * 물리적인 image upload path 제공
//     *
//     * @return upload path
//     */
//    @Transient  // 매핑하지 않는다.
//    public String getImageUploadPath() {
//        return String.format("%s/product/%d", System.getProperty(SystemPropertiesConfig.STORAGE_PATH), post.getId());
//    }
//
//    /**
//     * url에서 file name 추출
//     *
//     * @param imageUrl image url
//     * @return file name
//     */
//    @Transient
//    public static String getFileNameFromUrl(String imageUrl) {
//        // uri/product/tmp/fileName
//        //Todo: confirm url pattern matching??
//        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
//    }
}
