package kr.co.mash_up.nine_tique.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "seller")
@Getter
@Setter
@ToString(exclude = {"shop", "user"})
@NoArgsConstructor  // JPA는 default constructor 필요
public class Seller extends AbstractEntity<Seller.Id> {

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne  // Seller(Many) : Shop(One)
    @MapsId(value = "shopId")
    private Shop shop;

    @OneToOne
    @MapsId(value = "userId")
    private User user;

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"shopId", "userId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "shop_id")
        private Long shopId;

        @Column(name = "user_id")
        private Long userId;
    }
}
