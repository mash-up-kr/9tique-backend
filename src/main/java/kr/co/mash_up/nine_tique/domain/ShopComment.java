package kr.co.mash_up.nine_tique.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ethankim on 2017. 7. 1..
 */
@Entity
@Table(name = "shop_comment")
@Getter
@Setter
@NoArgsConstructor  // JPA는 default constructor 필요
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class ShopComment extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;  // 댓글 내용

    @ManyToOne  // ShopComment(Many) : User(One)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_shop_comment_to_user_id"))
    private User user;

    @ManyToOne  // ShopComment(Many) : Shop(One)
    @JoinColumn(name = "shop_id", foreignKey = @ForeignKey(name = "fk_shop_comment_to_shop_id"))
    private Shop shop;
}
