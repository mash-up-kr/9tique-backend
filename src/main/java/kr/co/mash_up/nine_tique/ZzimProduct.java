package kr.co.mash_up.nine_tique;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "zzim_product")
@Getter
@Setter
@ToString  //(exclude = {"like", "product"})
@NoArgsConstructor  // JPA는 default constructor 필요
public class ZzimProduct extends AbstractEntity<ZzimProduct.Id> {

    @EmbeddedId  //복합키 설정
    private Id id = new Id();

    @ManyToOne  // ZzimProduct(Many) : Like(One)
    @MapsId(value = "likeId")  //식별관계 매핑(FK가 PK에 포함될 때). FK와 매핑한 연관관계를 기본키에도 매핑하겠다는 뜻
    private Zzim zzim;

    @ManyToOne
    @MapsId(value = "productId")
    @JsonProperty
    @JsonUnwrapped
    private Product product;

    //Todo: 필요한 필드 추가

    public ZzimProduct(Zzim zzim, Product product){
        this.id.zzimId = zzim.getId();
        this.id.productId = product.getId();
        this.zzim = zzim;
        this.product = product;
    }

    @JsonProperty(value = "createdAt")
    public Long getCreatedTimestamp(){
        if(this.createdAt == null){
            return null;
        }
        return Timestamp.valueOf(this.createdAt).getTime();
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
}
