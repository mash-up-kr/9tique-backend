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

@Entity
@Table(name = "product_image")
@Getter
@Setter
@NoArgsConstructor  // JPA는 default constructor 필요
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class ProductImage extends AbstractEntity<ProductImage.Id> {

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne  // ProductImage(Many) : Image(One)
    @MapsId(value = "imageId")
    private Image image;

    @ManyToOne  // ProductImage(Many) : Product(One)
    @MapsId(value = "productId")
    private Product product;

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"productId", "imageId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "product_id", columnDefinition = "INT(11)")
        private Long productId;

        @Column(name = "image_id", columnDefinition = "INT(11)")
        private Long imageId;
    }

    public ProductImage(Product product, Image image) {
        this.id.productId = product.getId();
        this.id.imageId = image.getId();
        this.product = product;
        this.image = image;
    }
}
