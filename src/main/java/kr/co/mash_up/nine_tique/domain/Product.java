package kr.co.mash_up.nine_tique.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString(exclude = {"shop", "category", "productImages"})
@NoArgsConstructor  // JPA는 default constructor 필요
public class Product extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // id의 수동적인 제어를 막기 위해 setter를 생성하지 않는다.

    @Column(length = 50)
    private String name;  //이름

    @Column(length = 50)
    private String brandName;

    @Column(length = 50)
    private String size;

    @Column
    private int price;

    @Lob  // text type으로 사용하기 위해
    private String description;  // 상세설명

    @Enumerated(EnumType.STRING)  // enum 이름을 DB에 저장
    private Status status;  // 판매중/완료

    //    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;  // 판매자 정보

    // Memo : Lazy 함부로 쓰지말자... 해당 테이블 쿼리 안날릴시 정보가 안나온다.
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "category_id")  // FK 매핑시 이용
    private Category category;  // 카테고리

    // One(Product) : Many(ProductImage) - Many쪽이 FK를 가지고(주인O), One쪽이 mappedBy(주인X)를 적용
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SellerProduct> sellerProducts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ZzimProduct> zzimProducts;

    //Todo: 이벤트 여부 추가?

    /**
     * 상품의 판매중/판매 완료 여부
     */
    public enum Status {
        SELL,  // 판매중
        SOLD_OUT  // 판매 완료
    }
}
