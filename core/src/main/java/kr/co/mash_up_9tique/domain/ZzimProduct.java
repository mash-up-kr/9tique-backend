package kr.co.mash_up_9tique.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "zzim_product")
@Getter
@Setter
@ToString(exclude = {"zzim", "product"})
@NoArgsConstructor  // JPA는 default constructor 필요
public class ZzimProduct extends AbstractEntity<ZzimProduct.Id> {

    @EmbeddedId  //복합키 설정
    private Id id = new Id();

    @ManyToOne  // ZzimProduct(Many) : Zzim(One)
    @MapsId(value = "zzimId")  //식별관계 매핑(FK가 PK에 포함될 때). FK와 매핑한 연관관계를 기본키에도 매핑하겠다는 뜻
    private Zzim zzim;

    @ManyToOne
    @MapsId(value = "productId")
//    @JsonProperty
//    @JsonUnwrapped  // json object로 wrapping 안한다
    private Product product;

    @Column
    private boolean enabled;

    public ZzimProduct(Zzim zzim, Product product){
        this.id.zzimId = zzim.getId();
        this.id.productId = product.getId();
        this.zzim = zzim;
        this.product = product;
        enabled = true;
    }

    public boolean matchProduct(Product newProduct){
        if(newProduct == null){
            return false;
        }
        return this.product.equals(newProduct);
    }

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"zzimId", "productId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "zzim_id")
        private Long zzimId;

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
