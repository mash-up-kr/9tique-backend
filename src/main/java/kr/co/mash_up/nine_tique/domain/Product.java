package kr.co.mash_up.nine_tique.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"productImages", "sellerProducts", "zzimProducts"})
@EqualsAndHashCode(callSuper = false, of = "id")
public class Product extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;  // 상품 이름

    @Column(name = "size", length = 20, nullable = false)
    private String size;  // 상품 사이즈

    @Column(name = "price", nullable = false, columnDefinition = "INT(11) default 0")
    private int price;  // 상품 가격

    @Lob  // text type으로 사용하기 위해
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;  // 상품 설명

    @Enumerated(EnumType.STRING)  // enum 이름을 DB에 저장
    @Column(name = "status", length = 20, nullable = false)
    private Status status;  // 상품 상태(판매중 / 완료)

    @Column(name = "zzim_count", columnDefinition = "INT(11) default 0")
    private Long zzimCount;  // 찜 갯수

    @Column(name = "active", length = 1, columnDefinition = "VARCHAR(1)")
    @Type(type = "yes_no")
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", foreignKey = @ForeignKey(name = "fk_product_to_brand_id"))
    private Brand brand;  // 브랜드 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", columnDefinition = "INT(11)", foreignKey = @ForeignKey(name = "fk_product_to_shop_id"))
    private Shop shop;  // 매장 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_product_to_category_id"))  // FK 매핑시 이용
    private Category category;  // 카테고리

    // One(Product) : Many(ProductImage) - Many쪽이 FK를 가지고(주인O), One쪽이 mappedBy(주인X)를 적용
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SellerProduct> sellerProducts;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ZzimProduct> zzimProducts;

    // Todo: 프로모션 상품, 포스트 상품 추가

    /**
     * 상품의 판매중/판매 완료 여부
     */
    public enum Status {
        SELL,  // 판매중
        SOLD_OUT  // 판매 완료
    }

    public boolean matchShop(Seller seller) {
        if (seller == null) {
            return false;
        }
        return this.shop.equals(seller.getShop());
    }

    public void update(Product newProduct, Category category) {
        this.name = newProduct.name;
        this.brand = newProduct.brand;
        this.size = newProduct.size;
        this.price = newProduct.price;
        this.description = newProduct.description;
        this.status = newProduct.status;
        this.category = category;
    }

    public void disable() {
        if (active) {
            this.active = false;
            productImages.forEach(ProductImage::deactive);
            sellerProducts.forEach(SellerProduct::disable);
            zzimProducts.forEach(ZzimProduct::disable);
        }
    }

    public void enable() {
        if (!active) {
            this.active = true;
            productImages.forEach(ProductImage::active);
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
}
