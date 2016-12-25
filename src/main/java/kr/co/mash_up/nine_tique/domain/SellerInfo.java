package kr.co.mash_up.nine_tique.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 인증코드를 입력한 판매자 정보
 */
@Entity
@Table(name = "seller_info")
@Getter
@Setter
@ToString(exclude = {"products"})
@NoArgsConstructor  // JPA는 default constructor 필요
public class SellerInfo extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String shopName;  // 매장이름

    @Column
    private String shopInfo;  // 매장정보

    //Todo:  매장위치 추가

    @Column(length = 20, nullable = false, unique = true)
    private String phone;  // 전화

    // mappedBy - 연관관계 주인 설정. 주인O(읽기, 쓰기), 주인X(읽기)
    // mappedBy가 있으면 주인X.
    @OneToMany(mappedBy = "sellerInfo", fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "sellerInfo")
    private List<Product> products = new ArrayList<>();

//    @OneToOne(fetch = FetchType.LAZY)
    @OneToOne
    @JoinColumn(name = "user_id")  // FK 매핑시 이용
    private User user;
}
