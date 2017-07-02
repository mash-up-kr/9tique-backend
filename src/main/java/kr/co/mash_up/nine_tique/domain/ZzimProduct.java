package kr.co.mash_up.nine_tique.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "zzim_product")
@Getter
@Setter
@NoArgsConstructor  // JPA는 default constructor 필요
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class ZzimProduct extends AbstractEntity<ZzimProduct.Id> {

    @EmbeddedId  //복합키 설정
    private Id id = new Id();

    @ManyToOne  // ZzimProduct(Many) : Zzim(One)
    @MapsId(value = "zzimId")  //식별관계 매핑(FK가 PK에 포함될 때). FK와 매핑한 연관관계를 기본키에도 매핑하겠다는 뜻
    private Zzim zzim;

    @ManyToOne  // ZzimProduct(Many) : Product(One)
    @MapsId(value = "productId")
    private Product product;

    @Type(type = "yes_no")
    @Column(name = "active", length = 1, columnDefinition = "VARCHAR(1)")
    private boolean active;

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"zzimId", "productId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "zzim_id")
        private Long zzimId;

        @Column(name = "product_id")
        private Long productId;
    }

    public ZzimProduct(Zzim zzim, Product product) {
        this.id.zzimId = zzim.getId();
        this.id.productId = product.getId();
        this.zzim = zzim;
        this.product = product;
        this.active = true;
    }

    public boolean matchProduct(Product newProduct) {
        if (newProduct == null) {
            return false;
        }
        return this.product.equals(newProduct);
    }

    public void disable() {
        if (active) {
            this.active = false;
        }
    }

    public void enable() {
        if (!active) {
            this.active = true;
        }
    }
}
