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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // id의 수동적인 제어를 막기 위해 setter를 생성하지 않는다.

    @Column(length = 50)
    private String name;  //이름

    @Column(length = 50)
    private String brandName;

    @Column(length = 50)
    private String size;

    @Column(columnDefinition = "integer default 0")
    private int price;

    @Lob  // text type으로 사용하기 위해
    private String description;  // 상세설명

    @Enumerated(EnumType.STRING)  // enum 이름을 DB에 저장
    private Status status;  // 판매중/완료

    @Column
    private boolean enabled;

    //    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_to_shop_id"))
    private Shop shop;  // 판매자 정보

    // Memo : Lazy 함부로 쓰지말자... 해당 테이블 쿼리 안날릴시 정보가 안나온다.
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_to_category_id"))  // FK 매핑시 이용
    private Category category;  // 카테고리

    // One(Product) : Many(ProductImage) - Many쪽이 FK를 가지고(주인O), One쪽이 mappedBy(주인X)를 적용
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SellerProduct> sellerProducts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ZzimProduct> zzimProducts;

    public boolean matchShop(Seller seller) {
        if (seller == null) {
            return false;
        }
        return this.shop.equals(seller.getShop());
    }

    public void update(Product newProduct, Category category) {
        this.name = newProduct.name;
        this.brandName = newProduct.brandName;
        this.size = newProduct.size;
        this.price = newProduct.price;
        this.description = newProduct.description;
        this.status = newProduct.status;
        this.category = category;
    }

    //Todo: 이벤트 여부 추가?

    /**
     * 상품의 판매중/판매 완료 여부
     */
    public enum Status {
        SELL,  // 판매중
        SOLD_OUT  // 판매 완료
    }

    public void disable() {
        if (enabled) {
            this.enabled = false;
            productImages.forEach(ProductImage::disable);
            sellerProducts.forEach(SellerProduct::disable);
            zzimProducts.forEach(ZzimProduct::disable);
        }
    }

    public void enable() {
        if (!enabled) {
            this.enabled = true;
            productImages.forEach(ProductImage::enable);
            sellerProducts.forEach(SellerProduct::enable);
            zzimProducts.forEach(ZzimProduct::enable);
        }
    }

    /**
     * 상품이 찜 되었는지 확인
     *
     * @param zzimProducts 유저의 찜한 상품 목록
     * @return 결과
     */
    public boolean checkProductZzim(List<ZzimProduct> zzimProducts) {
        for (ZzimProduct zzimProduct : zzimProducts) {
            if (zzimProduct.matchProduct(this)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 상품이 내가 등록한 상품인지 확인
     *
     * @param sellerProducts 판매자가 등록한 상품 목록
     * @return 내가 등록한 상품인지 여부
     */
    public boolean checkSeller(List<SellerProduct> sellerProducts) {
        for (SellerProduct sellerProduct : sellerProducts) {
            if (sellerProduct.matchProduct(this)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id != null ? id.equals(product.id) : product.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
