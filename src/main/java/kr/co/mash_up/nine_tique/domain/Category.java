package kr.co.mash_up.nine_tique.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor  // JPA는 default constructor 필요
@ToString(exclude = {"products"})
@EqualsAndHashCode(callSuper = false, of = "id")
public class Category extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;  // Todo: id의 수동적인 제어를 막기 위해 setter를 생성하지 않는다.

    @Column(name = "main", length = 255, nullable = false)
    private String main;   // 상위 카테고리 이름

    @Column(name = "sub", length = 255)
    private String sub;  // 하위 카테고리 이름

    @Column(name = "active", length = 1, columnDefinition = "VARCHAR(1)")
    @Type(type = "yes_no")
    private boolean active;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    //Todo: products count도 넣을까?

    public void disable() {
        if (active) {
            this.active = false;
        }
    }

    public void enable() {
        if (!active) {
            this.active = true;
        }
    }

    public void update(Category newCategory) {
        this.main = newCategory.main;
        this.sub = newCategory.sub;
    }
}
