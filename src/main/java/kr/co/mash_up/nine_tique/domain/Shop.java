package kr.co.mash_up.nine_tique.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 인증코드를 입력한 판매자 정보
 */
@Entity
@Table(name = "shop")
@Getter
@Setter
@NoArgsConstructor  // JPA는 default constructor 필요
@ToString(exclude = {"products", "sellers"})
@EqualsAndHashCode(callSuper = false, of = "id")
public class Shop extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;  // 매장 이름

    @Column(name = "description", length = 255, nullable = false)
    private String description;  // 매장 설명

    @Column(name = "phone_no", length = 20, nullable = false, unique = true)
    private String phoneNumber;  // 매장 전화번호

    @Column(name = "kakao_open_chat_url", length = 255)
    private String kakaoOpenChatUrl;  // kakao open chat url

    @Column(name = "comment_count", nullable = false, columnDefinition = "INT(11) default '0'")
    private Long commentCount;  // 매장의 댓글 갯수

    // Todo: 매장위치 추가

    @Column(name = "active", length = 1, columnDefinition = "VARCHAR(1)")
    @Type(type = "yes_no")
    private boolean active;

    // mappedBy - 연관관계 주인 설정. 주인O(읽기, 쓰기), 주인X(읽기)
    // mappedBy가 있으면 주인X.
    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Product> products;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Seller> sellers;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<ShopComment> shopComments;

    public void deactive() {
        if (active) {
            this.active = false;
            products.forEach(Product::disable);
            sellers.forEach(Seller::disable);
            shopComments.forEach(ShopComment::deactive);
        }
    }

    public void active() {
        if (!active) {
            this.active = true;
            products.forEach(Product::enable);
            sellers.forEach(Seller::enable);
            shopComments.forEach(ShopComment::active);
        }
    }

    public void update(Shop newShop) {
        this.name = newShop.name;
        this.description = newShop.description;
        this.phoneNumber = newShop.phoneNumber;
        this.kakaoOpenChatUrl = newShop.kakaoOpenChatUrl;
    }
}
