package kr.co.mash_up.nine_tique.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "seller")
@Getter
@Setter
@ToString(exclude = {"shop", "user"})
@NoArgsConstructor  // JPA는 default constructor 필요
public class Seller extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne  // Seller(Many) : Shop(One)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_seller_to_shop_id"))
    private Shop shop;

    @OneToOne
//    @MapsId
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_seller_to_user_id"))
    private User user;

    @Column
    private boolean enabled;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SellerProduct> sellerProducts;

    public Seller(Shop shop, User user) {
        this.shop = shop;
        this.user = user;
        this.enabled = true;
    }

    public void addSellerProduct(SellerProduct sellerProduct) {
        this.sellerProducts.add(sellerProduct);
    }

    public void disable() {
        if (enabled) {
            this.enabled = false;
            sellerProducts.forEach(SellerProduct::disable);
        }
    }

    public void enable() {
        if (!enabled) {
            this.enabled = true;
            sellerProducts.forEach(SellerProduct::enable);
        }
    }

    public boolean matchShop(Shop shop) {
        if (shop == null) {
            return false;
        }
        return this.shop.equals(shop);
    }
}
