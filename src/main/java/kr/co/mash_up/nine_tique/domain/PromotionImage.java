package kr.co.mash_up.nine_tique.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;

import kr.co.mash_up.nine_tique.config.SystemPropertiesConfig;
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
@Table(name = "promotion_image")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class PromotionImage extends AbstractEntity<PromotionImage.Id> {

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne  // PromotionImage(Many) : Image(One)
    @MapsId
    private Image image;

    @ManyToOne  // PromotionImage(Many) : Promotion(One)
    @MapsId
    private Promotion promotion;

    @Column(name = "active", length = 1, columnDefinition = "VARCHAR(1)")
    @Type(type = "yes_no")
    private boolean active;

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"promotionId", "imageId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "promotion_id", columnDefinition = "INT(11)")
        private Long promotionId;

        @Column(name = "image_id", columnDefinition = "INT(11)")
        private Long imageId;
    }

    public PromotionImage(Promotion promotion, Image image) {
        this.id.promotionId = promotion.getId();
        this.id.imageId = image.getId();
        this.promotion = promotion;
        this.image = image;
    }
}
