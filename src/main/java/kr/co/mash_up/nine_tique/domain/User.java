package kr.co.mash_up.nine_tique.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import kr.co.mash_up.nine_tique.security.Authorities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 유저
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor  // JPA는 default constructor 필요
@ToString(exclude = {"seller", "zzim"})
@EqualsAndHashCode(callSuper = false, of = "id")
public class User extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    @Column(name = "name", length = 20)
    private String name;  // 이름

    @Column(name = "email", length = 30)
    private String email;  // 이메일

    @Column(name = "oauth_token", length = 256)
    private String oauthToken;  // 카톡, FB, token Todo: 필요없을 시 제거

    @Enumerated(EnumType.STRING)  // enum 이름을 DB에 저장
    @Column(name = "oauth_type", length = 20)
    private OauthType oauthType;

    @Column(name = "profile_image_url", length = 256)
    private String profileImageUrl;

    // Todo: 푸시 구현시 주석 해제
//    @Column(name = "cloud_msg_reg_id", length = 256)
//    private String cloudMsgRegId;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Seller seller;

    // mappedBy - 연관관계 주인 설정. 주인O(읽기, 쓰기), 주인X(읽기)
    // mappedBy가 있으면 주인X.
    // orphanRemoval 연관관계가 끊어진 엔티티를 자동으로 삭제
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Zzim zzim;

    /**
     * 사용자는 여러개의 권한을 가질 수 있도록 N:N 매핑
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "authority_id", columnDefinition = "INT(11)"))
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

    public boolean alreadySeller(){
        return this.seller != null;
    }
}
