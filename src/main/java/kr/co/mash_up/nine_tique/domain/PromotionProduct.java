package kr.co.mash_up.nine_tique.domain;

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
 * Promotion과 Product의 관계
 * <p>
 * Created by ethankim on 2017. 7. 2..
 */
@Entity
@Table(name = "promotion_product")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class PromotionProduct {

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne  // PromotionProduct(Many) : Promotion(One)
    @MapsId(value = "promotionId")
    private Promotion promotion;

    @ManyToOne  // PromotionProduct(Many) : Product(One)
    @MapsId(value = "productId")
    private Product product;

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"promotionId", "productId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "promotion_id", columnDefinition = "INT(11)")
        private Long promotionId;

        @Column(name = "product_id", columnDefinition = "INT(11)")
        private Long productId;
    }

    public PromotionProduct(Promotion promotion, Product product) {
        this.id.promotionId = promotion.getId();
        this.id.productId = product.getId();
        this.promotion = promotion;
        this.product = product;
    }
}
