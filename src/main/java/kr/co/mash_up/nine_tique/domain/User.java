package kr.co.mash_up.nine_tique.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

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
    private Long id;

    @Column(length = 20, nullable = false)  // not null
    @JsonProperty
    private String name;

    @Column(length = 30, nullable = false, unique = true)  // not null, unique
    @JsonProperty
    private String email;

    @Column(length = 255)
    private String oauthToken;  // 카톡, FB

    @Column(nullable = false, unique = true)
    private String accessToken;  // api 접근 인증용

//    private String gcmToken;  // 푸쉬

    // mappedBy - 연관관계 주인 설정. 주인O(읽기, 쓰기), 주인X(읽기)
    // mappedBy가 있으면 주인X.
//    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @OneToOne(mappedBy = "user")
    private SellerInfo sellerInfo;

    // orphanRemoval 연관관계가 끊어진 엔티티를 자동으로 삭제
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Zzim zzim;

    //Todo: 권한 Entity 추가

}
