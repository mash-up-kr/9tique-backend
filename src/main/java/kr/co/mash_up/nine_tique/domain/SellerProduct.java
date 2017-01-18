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

    public SellerProduct(Seller seller, Product product){
        this.id.sellerId = seller.getId();
        this.id.productId = product.getId();
        this.seller = seller;
        this.product = product;
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
}
