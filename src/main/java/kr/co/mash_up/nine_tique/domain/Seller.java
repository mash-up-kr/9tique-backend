package kr.co.mash_up.nine_tique.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seller")
@Getter
@Setter
@ToString(exclude = {"shop", "user"})
@NoArgsConstructor  // JPA는 default constructor 필요
public class Seller extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  // Seller(Many) : Shop(One)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToOne
//    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SellerProduct> sellerProducts = new ArrayList<>();

    public Seller(Shop shop, User user) {
        this.shop = shop;
        this.user = user;
    }
}
