package kr.co.mash_up.nine_tique.domain;

import javax.persistence.CascadeType;
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
@Table(name = "promotion_image")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class PromotionImage extends AbstractEntity<PromotionImage.Id> {

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne(cascade = CascadeType.ALL)  // PromotionImage(Many) : Image(One)
    @MapsId(value = "imageId")
    private Image image;

    @ManyToOne  // PromotionImage(Many) : Promotion(One)
    @MapsId(value = "promotionId")
    private Promotion promotion;

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
