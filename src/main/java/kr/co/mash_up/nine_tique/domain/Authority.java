package kr.co.mash_up.nine_tique.domain;

/*
Spring Security는 Application의 보안을 강화하는데 사용하는 프레임워크
Authentication, Authorization, Access Control 처리를 간단한 설정만으로 쉽게 적용 가능
 */

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Spring Security에서 권한 별로 Access Control하기 때문에 Authority 엔티티 필요
 */
@Entity
@Table(name = "authority")
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
public class Authority extends AbstractEntity<Long> implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Long id;

    // 권한 이름
    @Column(name = "authority", length = 20)
    private String authority;
}
