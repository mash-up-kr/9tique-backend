package kr.co.mash_up.nine_tique.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "seller_product")
@Getter
@Setter
@ToString(exclude = {"seller", "product"})
@NoArgsConstructor
public class SellerProduct extends AbstractEntity<SellerProduct.Id> {

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne  // SellerProduct(Many) : Seller(One)
    @MapsId(value = "sellerId")
    private Seller seller;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId(value = "productId")
    private Product product;

    @Column
    private boolean enabled;

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

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"sellerId", "productId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "seller_id")
        private Long sellerId;

        @Column(name = "product_id")
        private Long productId;
    }

    public void disable() {
        if (enabled) {
            this.enabled = false;
        }
    }

    public void enable() {
        if (!enabled) {
            this.enabled = true;
        }
    }
}
