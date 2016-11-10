package kr.co.mash_up.nine_tique;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 유저
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor  // JPA는 default constructor 필요
public class User extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // id의 수동적인 제어를 막기 위해 setter를 생성하지 않는다.

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column
    private String oauthToken;  // 카톡, FB

    @Column(nullable = false, unique = true)
    private String accessToken;  // api 접근 인증용

//    private String gcmToken;  // 푸쉬

    //Todo: 권한 Entity 추가

    // mappedBy - 연관관계 주인 설정. 주인O(읽기, 쓰기), 주인X(읽기)
    // mappedBy가 있으면 주인X.
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private SellerInfo sellerInfo;

    /**
     * test등의 용도로 setter 생성
     * @param id
     */
    public void setId(Long id){
        this.id = id;
    }



}
