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
 * 게시물(컨텐츠 등...)
 * <p>
 * Created by ethankim on 2017. 7. 1..
 */
@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"postProducts", "postComments", "postImages"})
@EqualsAndHashCode(callSuper = false, of = "id")
public class Post extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;  // 게시물 이름(제목)

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;  // 게시물 내용(형식이 복잡할 수 있다)

    @Column(name = "comment_count", nullable = false, columnDefinition = "INT(11) default 0")
    private Long commentCount;  // 게시물의 댓글 갯수

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostComment> postComments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostProduct> postProducts;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostImage> postImages;
}
