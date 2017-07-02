package kr.co.mash_up.nine_tique.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ethankim on 2017. 6. 30..
 */

@Entity
@Table(name = "brand")
@Getter
@Setter
@NoArgsConstructor  // JPA는 default constructor 필요
@ToString(exclude = {"products"})
@EqualsAndHashCode(callSuper = false, of = "id")
public class Brand extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    @Column(name = "name", length = 50, unique = true)
    private String name;  // 브랜드 이름

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Product> products;
}
