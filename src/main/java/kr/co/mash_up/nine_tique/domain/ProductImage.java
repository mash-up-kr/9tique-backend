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
    @MapsId
    private Image image;

    @ManyToOne  // ProductImage(Many) : Product(One)
    @MapsId
    private Product product;

    @Column(name = "active", length = 1, columnDefinition = "VARCHAR(1)")
    @Type(type = "yes_no")
    private boolean active;

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"productId", "imageId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "product_id", columnDefinition = "INT(11)")
        private Long productId;

        @Column(name = "image_id", columnDefinition = "INT(11)")
        private Long imageId;
    }

    public void deactive() {
        if (active) {
            this.active = false;
        }
    }

    public void active() {
        if (!active) {
            this.active = true;
        }
    }
}
