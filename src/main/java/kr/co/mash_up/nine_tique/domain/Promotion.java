package kr.co.mash_up.nine_tique.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String name;  // 프로모션 이름

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;  // 프로모션 설명

    @Column(name = "priority", columnDefinition = "INT(11)")
    private Integer priority;  // 우선 순위(높은 것 우선)

    @Column(name = "register")
    private String register;  // 프로모션을 등록한 사람

    @Column(name = "start_at")
    private LocalDateTime startAt;  // 프로모션 시작 일시

    @Column(name = "end_at")
    private LocalDateTime endAt;  // 프로모션 종료 일시

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private Status status;  // 프로모션 상태

    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionImage> promotionImages;

    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionProduct> promotionProducts;

    public void addProduct(PromotionProduct promotionProduct) {
        promotionProducts.add(promotionProduct);
    }

    public void addImage(PromotionImage image) {
        promotionImages.add(image);
    }

    /**
     * 프로모션 상태
     */
    public enum Status {
        SAVED,
        RUNNING,  // 실행 중
        PAUSE,  // 일시 중지
        END;  // 종료
    }
}
