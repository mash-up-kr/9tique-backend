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
 * 게시물의 댓글
 * <p>
 * Created by ethankim on 2017. 7. 1..
 */
@Entity
@Table(name = "post_comment")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class PostComment extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    @Column(name = "contents", length = 255, nullable = false)
    private String contents;  // 댓글 내용

    @ManyToOne  // PostComment(Many) : Post(One)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_post_comment_to_post_id"))
    private Post post;

    @ManyToOne  // PostComment(Many) : User(One)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_post_comment_to_user_id"))
    private User writer;

    public boolean matchWriter(User user) {
        return this.writer.equals(user);
    }

    public void update(String contents) {
        this.contents = contents;
    }

    public PostComment(Post post, User writer, String contents) {
        this.post = post;
        this.writer = writer;
        this.contents = contents;
    }
}
