package kr.co.mash_up.nine_tique.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 찜(좋아요)
 */
@Entity
@Table(name = "zzim")
@Getter
@Setter
@ToString(exclude = {"user"})
@NoArgsConstructor  // JPA는 default constructor 필요
public class Zzim extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
//    @MapsId  //식별관계 매핑(FK가 PK에 포함될 때). FK와 매핑한 연관관계를 기본키에도 매핑하겠다는 뜻
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "zzim", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ZzimProduct> zzimProducts = new ArrayList<>();
}
