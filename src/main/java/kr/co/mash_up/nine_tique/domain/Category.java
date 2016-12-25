package kr.co.mash_up.nine_tique.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@ToString(exclude = {"products"})
@NoArgsConstructor  // JPA는 default constructor 필요
public class Category extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;  // id의 수동적인 제어를 막기 위해 setter를 생성하지 않는다.

    @Column(nullable = false)
    @JsonProperty
    private String main;

    @Column
    @JsonProperty
    private String sub;

    //Todo: User와의 관계 추가 -> ???
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    //Todo: products count도 넣을까?

}
