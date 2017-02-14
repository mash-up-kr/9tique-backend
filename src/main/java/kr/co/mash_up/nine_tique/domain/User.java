package kr.co.mash_up.nine_tique.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.mash_up.nine_tique.security.Authorities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 유저
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@ToString(exclude = {"seller", "zzim"})
@NoArgsConstructor  // JPA는 default constructor 필요
public class User extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //    @Column(length = 20, nullable = false)  // not null
    @Column(length = 20)
    @JsonProperty
    private String name;

    //    @Column(length = 30, nullable = false, unique = true)  // not null, unique
    @Column(length = 30)
    @JsonProperty
    private String email;

    @Column(length = 255)
    private String oauthToken;  // 카톡, FB

    @Enumerated(EnumType.STRING)  // enum 이름을 DB에 저장
    private OauthType oauthType;

    //    @Column(nullable = false, unique = true)
//    private String accessToken;  // api 접근 인증용

//    private String gcmToken;  // 푸쉬

    //    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Seller seller;

    // mappedBy - 연관관계 주인 설정. 주인O(읽기, 쓰기), 주인X(읽기)
    // mappedBy가 있으면 주인X.
    // orphanRemoval 연관관계가 끊어진 엔티티를 자동으로 삭제
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Zzim zzim;

    /**
     * 사용자는 여러개의 권한을 가질 수 있도록 N:N 매핑
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities = new HashSet<>();

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    /**
     * 사용자로부터 권한 조회
     *
     * @return 조회된 권한
     */
    public Set<GrantedAuthority> getAuthoritiesWithoutPersistence() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        this.authorities.forEach(authority -> authorities.add(authority));
        return authorities;
    }

    /**
     * oauth type 관리
     */
    public enum OauthType {
        KAKAO,
        FB
    }

    public User(String oauthToken, User.OauthType oauthType, HashSet<Authority> authorities) {
        this.oauthToken = oauthToken;
        this.oauthType = oauthType;
        this.authorities = authorities;
    }

    public boolean matchAuthority(String strAuthority) {
        for (Authority authority : authorities) {
            if (strAuthority.equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public String findAuthority() {
        String result = Authorities.USER;
        for (Authority authority : this.authorities) {
            String tmp = authority.getAuthority();
            if (tmp.equals(Authorities.ADMIN)) {
                result = Authorities.ADMIN;
                break;
            } else if (tmp.equals(Authorities.SELLER) && !result.equals(Authorities.ADMIN)) {
                result = Authorities.SELLER;
            }
        }
        return result;
    }
}
