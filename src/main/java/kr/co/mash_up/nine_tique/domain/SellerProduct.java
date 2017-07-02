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

@Entity
@Table(name = "seller_product")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class SellerProduct extends AbstractEntity<SellerProduct.Id> {

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne  // SellerProduct(Many) : Seller(One)
    @MapsId(value = "sellerId")
    private Seller seller;

    @ManyToOne  // SellerProduct(Many) : Product(One)
    @MapsId(value = "productId")
    private Product product;

    @Column(name = "active", length = 1, columnDefinition = "VARCHAR(1)")
    @Type(type = "yes_no")
    private boolean active;

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"sellerId", "productId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "seller_id", columnDefinition = "INT(11)")
        private Long sellerId;

        @Column(name = "product_id", columnDefinition = "INT(11)")
        private Long productId;
    }

    public SellerProduct(Seller seller, Product product) {
        this.id.sellerId = seller.getId();
        this.id.productId = product.getId();
        this.seller = seller;
        this.product = product;
        this.active = true;
    }

    public boolean matchProduct(Product newProduct) {
        if (newProduct == null) {
            return false;
        }
        return this.product.equals(newProduct);
    }

    public void disable() {
        if (active) {
            this.active = false;
        }
    }

    public void enable() {
        if (!active) {
            this.active = true;
        }
    }
}
