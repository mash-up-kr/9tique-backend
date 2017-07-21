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
 * Created by ethankim on 2017. 7. 20..
 */
@Entity
@Table(name = "shop_image")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class ShopImage extends AbstractEntity<ShopImage.Id> {

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne  // ShopImage(Many) : Shop(One)
    @MapsId(value = "shopId")
    private Shop shop;

    @ManyToOne  // ShopImage(Many) : Image(One)
    @MapsId(value = "imageId")
    private Image image;

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"shopId", "imageId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "shop_id", columnDefinition = "INT(11)")
        private Long shopId;

        @Column(name = "image_id", columnDefinition = "INT(11)")
        private Long imageId;
    }

    public ShopImage(Shop shop, Image image) {
        this.id.shopId = shop.getId();
        this.id.imageId = image.getId();
        this.shop = shop;
        this.image = image;
    }
}
