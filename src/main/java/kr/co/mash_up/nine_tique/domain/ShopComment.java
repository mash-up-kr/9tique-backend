package kr.co.mash_up.nine_tique.domain;

import org.hibernate.annotations.Type;

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

    @Column(name = "contents", nullable = false)
    private String contents;  // 댓글 내용

    @Column(name = "active", length = 1, columnDefinition = "VARCHAR(1)")
    @Type(type = "yes_no")
    private boolean active;

    @ManyToOne  // ShopComment(Many) : User(One)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_shop_comment_to_user_id"))
    private User writer;

    @ManyToOne  // ShopComment(Many) : Shop(One)
    @JoinColumn(name = "shop_id", foreignKey = @ForeignKey(name = "fk_shop_comment_to_shop_id"))
    private Shop shop;

    public void active() {
        if (!active) {
            this.active = true;
        }
    }

    public void deactive() {
        if (active) {
            this.active = false;
        }
    }

    public boolean matchWriter(User user) {
        return this.writer.equals(user);
    }

    public void update(String contents) {
        this.contents = contents;
    }
}
