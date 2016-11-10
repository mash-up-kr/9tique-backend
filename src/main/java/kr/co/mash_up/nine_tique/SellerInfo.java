package kr.co.mash_up.nine_tique;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 인증코드를 입력한 판매자 정보
 */
@Entity
@Table(name = "seller_info")
@Getter
@Setter
@ToString
@NoArgsConstructor  // JPA는 default constructor 필요
public class SellerInfo extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // id의 수동적인 제어를 막기 위해 setter를 생성하지 않는다.

    //Todo: 매장정보

    //Todo:  매장위치

    @Column(length = 20, nullable = false, unique = true)
    private String phone;  // 전화

    // mappedBy - 연관관계 주인 설정. 주인O(읽기, 쓰기), 주인X(읽기)
    // mappedBy가 있으면 주인X.
    @OneToMany(mappedBy = "sellerInfo", fetch = FetchType.LAZY)
    private List<Product> products;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
