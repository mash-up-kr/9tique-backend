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
 * Created by ethankim on 2017. 7. 2..
 */
@Entity
@Table(name = "post_image")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class PostImage extends AbstractEntity<PostImage.Id> {

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne  // PostImage(Many) : Image(One)
    @MapsId(value = "imageId")
    private Image image;

    @ManyToOne  // PostImage(Many) : Post(One)
    @MapsId(value = "postId")
    private Post post;

    @Embeddable
    @Data
    @EqualsAndHashCode(callSuper = false, of = {"postId", "imageId"})
    public static class Id extends AbstractEntityId {

        @Column(name = "post_id", columnDefinition = "INT(11)")
        private Long postId;

        @Column(name = "image_id", columnDefinition = "INT(11)")
        private Long imageId;
    }

    public PostImage(Post post, Image image) {
        this.id.postId = post.getId();
        this.id.imageId = image.getId();
        this.post = post;
        this.image = image;
    }
}
