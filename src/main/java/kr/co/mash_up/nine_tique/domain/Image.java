package kr.co.mash_up.nine_tique.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 이미지를 나타내는 도메인 클래스
 * <p>
 * Created by ethankim on 2017. 7. 13..
 */
@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
public class Image extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    //Todo: length 조정. 중복방지용, 36byte(32글자 + 확장자)
    @Column(name = "file_name", length = 255, nullable = false, unique = true)
    private String fileName;  // 업로드한 이미지 파일 이름(서버에서 중복되지 않게 재생성)

    // Todo: length 조정. 260byte(window 최대 256글자 + 확장자)
    @Column(name = "original_file_name", length = 255, nullable = false)
    private String originalFileName;  // 업로드한 이미지 파일 원본 이름

    @Column(name = "size", columnDefinition = "INT(11)")
    private Long size;  // 파일 사이즈

    // Todo: 클라우드 스토리지 이용시 사용 고려
//    @Column(length = 255, nullable = false)
//    private String imageUrl;
}
