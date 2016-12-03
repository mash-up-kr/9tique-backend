package kr.co.mash_up.nine_tique.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.config.SystemPropertiesConfig;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@NoArgsConstructor  // JPA는 default constructor 필요
public class Product extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;  // id의 수동적인 제어를 막기 위해 setter를 생성하지 않는다.

    @Column(length = 50)
    @JsonProperty
    private String name;  //이름

    private String brandName;

    private String size;

    private int price;

    @Lob  // text type으로 사용하기 위해
    @JsonProperty
    private String description;  // 상세설명

    @Enumerated(EnumType.STRING)  // enum이름을 DB에 저장
    private ProductStatus productStatus;  // 판매중/완료

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_info_id")
    private SellerInfo sellerInfo;  // 판매자 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")  // FK 매핑시 이용
    private Category category;  // 카테고리

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)  // FK는 항상 N쪽에, 주인도 N쪽
    private Set<ProductImage> productImages;  //Todo: 이미지 4개로 제한

    //Todo: 이벤트 여부 추가?

    /**
     * test등의 용도로 setter 생성
     * @param id
     */
    public void setId(Long id){
        this.id = id;
    }


}
