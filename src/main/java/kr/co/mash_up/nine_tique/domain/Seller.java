package kr.co.mash_up.nine_tique.domain;

import javax.persistence.*;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "seller")
@Getter
@Setter
@NoArgsConstructor  // JPA는 default constructor 필요
@ToString(exclude = {"sellerProducts"})
@EqualsAndHashCode(callSuper = false, of = "id")
public class Seller extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    @Column(name = "authenti_code", length = 20, unique = true, updatable = false)
    private String authentiCode;  // 인증 코드

    @ManyToOne  // Seller(Many) : Shop(One)
    @JoinColumn(name = "shop_id", columnDefinition = "INT(11)", foreignKey = @ForeignKey(name = "fk_seller_to_shop_id"))
    private Shop shop;

    @OneToOne  // Seller(One) : User(One)
    @JoinColumn(name = "user_id", columnDefinition = "INT(11)", foreignKey = @ForeignKey(name = "fk_seller_to_user_id"))
    private User user;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SellerProduct> sellerProducts;

    public Seller(Shop shop, User user) {
        this.shop = shop;
        this.user = user;
    }

    public Seller(Shop shop, String authentiCode) {
        this.shop = shop;
        this.authentiCode = authentiCode;
    }

    public void registerSeller(User user) {
        this.user = user;
    }

    public void addSellerProduct(SellerProduct sellerProduct) {
        this.sellerProducts.add(sellerProduct);
    }

    public boolean matchShop(Shop shop) {
        if (shop == null) {
            return false;
        }
        return this.shop.equals(shop);
    }

    public void updateShop(Shop newShop) {
        this.shop.update(newShop);
    }

    public void updateName(String name) {
        this.user.updateName(name);
    }

    /**
     * 인증코드로 다른 유저가 판매자로 등록되었는지 확인
     *
     * @return
     */
    public boolean alreadyRegistration() {
        return this.user != null;
    }
}
