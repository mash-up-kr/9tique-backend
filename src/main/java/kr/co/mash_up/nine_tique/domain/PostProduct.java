package kr.co.mash_up.nine_tique.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Post와 Product의 관계
 * <p>
 * Created by ethankim on 2017. 7. 1..
 */
@Entity
@Table(name = "post_product")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class PostProduct extends AbstractEntity<PostProduct.Id> {

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne  // PostProduct(Many) : Post(One)
    @MapsId(value = "postId")
    private Post post;

    @ManyToOne  // PostProduct(Many) : Product(One)
    @MapsId(value = "productId")
    private Product product;

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"postId", "productId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "product_id", columnDefinition = "INT(11)")
        private Long productId;

        @Column(name = "post_id", columnDefinition = "INT(11)")
        private Long postId;
    }

    public PostProduct(Post post, Product product) {
        this.id.postId = post.getId();
        this.id.productId = product.getId();
        this.post = post;
        this.product = product;
    }
}
