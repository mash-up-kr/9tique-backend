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
    }

    public boolean matchProduct(Product newProduct) {
        if (newProduct == null) {
            return false;
        }
        return this.product.equals(newProduct);
    }
}
