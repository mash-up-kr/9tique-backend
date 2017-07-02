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
 * 프로모션
 * <p>
 * Created by ethankim on 2017. 7. 2..
 */
@Entity
@Table(name = "promotion")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"promotionImages", "promotionProducts"})
@EqualsAndHashCode(callSuper = false, of = "id")
public class Promotion extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private List<PromotionImage> promotionImages;

    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private List<PromotionProduct> promotionProducts;
}
